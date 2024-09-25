package pe.edu.cibertec.carros_frontend.dto;

public record ConsultaResponseDTO(String placa, String mensaje, String marca, String modelo, Integer nroAsientos, Double precio, String color) {
}
