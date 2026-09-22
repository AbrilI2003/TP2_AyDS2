package AyDS2.TP2.dto;

public class ApiResponse<T> {

    private int status;
    private String messege;
    private T data;

    public ApiResponse(int status, String messege, T data) {
        this.status = status;
        this.messege = messege;
        this.data = data;
    }

    // Getters (Spring los necesita para convertir esto a JSON)
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