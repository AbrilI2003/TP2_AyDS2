package AyDS2.TP2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import AyDS2.TP2.dto.ConversionDTO;
import AyDS2.TP2.dto.FrankfurterResponseDTO;
import AyDS2.TP2.dto.HistorialItemDTO;
import AyDS2.TP2.entity.HistorialConversion;
import AyDS2.TP2.exception.ExternalApiException;
import AyDS2.TP2.repository.HistorialConversionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DivisaService {

    private static final String FRANKFURTER_URL = "https://api.frankfurter.dev/v1/latest";

    private final RestClient restClient = RestClient.builder().build();

    private final HistorialConversionRepository historialRepository;

    public DivisaService(HistorialConversionRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public ConversionDTO convertir(double monto, String origen, String destino) {

        String origenNormalizado = origen.toUpperCase();
        String destinoNormalizado = destino.toUpperCase();

        String url = FRANKFURTER_URL + "?amount=" + monto
                + "&base=" + origenNormalizado
                + "&symbols=" + destinoNormalizado;

        FrankfurterResponseDTO respuestaExterna;

        try {
            respuestaExterna = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(FrankfurterResponseDTO.class);

        } catch (RestClientResponseException ex) {
            throw new ExternalApiException(
                    "El servicio de divisas no pudo procesar la solicitud: " + ex.getMessage());
        } catch (ResourceAccessException ex) {
            throw new ExternalApiException(
                    "No se pudo conectar con el servicio de divisas: " + ex.getMessage());
        }

        if (respuestaExterna == null || respuestaExterna.getRates() == null
                || !respuestaExterna.getRates().containsKey(destinoNormalizado)) {
            throw new ExternalApiException(
                    "La moneda de destino '" + destinoNormalizado + "' no está disponible");
        }

        double montoConvertido = respuestaExterna.getRates().get(destinoNormalizado);
        double tasaCambio = montoConvertido / monto;

        return new ConversionDTO(
                monto,
                origenNormalizado,
                destinoNormalizado,
                tasaCambio,
                montoConvertido,
                respuestaExterna.getDate()
        );
    }

    @Transactional
    public ConversionDTO consultarYGuardar(double monto, String origen, String destino) {

        // Reutilizamos el método de arriba
        ConversionDTO conversion = convertir(monto, origen, destino);

        // Guardamos en la BD
        HistorialConversion historial = new HistorialConversion(
                conversion.getMonedaOrigen(),
                conversion.getMonedaDestino(),
                BigDecimal.valueOf(conversion.getMontoOriginal()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(conversion.getMontoConvertido()).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(conversion.getTasaCambio()).setScale(6, RoundingMode.HALF_UP),
                LocalDateTime.now()
        );

        historialRepository.save(historial);

        return conversion;
    }

    @Transactional(readOnly = true)
    public List<HistorialItemDTO> obtenerHistorial(String origen, String destino) {

        return historialRepository
                .findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
                        origen.toUpperCase(), destino.toUpperCase())
                .stream()
                .map(h -> new HistorialItemDTO(h.getFechaConsulta(), h.getTasa()))
                .collect(Collectors.toList());
    }
}