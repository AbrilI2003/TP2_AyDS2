package AyDS2.TP2.dto;

public class ApiResponseDTO<T> {

    private int status;
    private String messege;
    private T data;

    public ApiResponseDTO(int status, String messege, T data) {
        this.status = status;
        this.messege = messege;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessege() {
        return messege;
    }

    public T getData() {
        return data;
    }
}