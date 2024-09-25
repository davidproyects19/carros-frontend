package pe.edu.cibertec.carros_frontend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.carros_frontend.dto.ConsultaRequestDTO;
import pe.edu.cibertec.carros_frontend.dto.ConsultaResponseDTO;
import pe.edu.cibertec.carros_frontend.viewmodel.RueditasModel;

@Controller
@RequestMapping("/consulta")
public class RueditasController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("inicio")
    public String inicio(Model model) {
        RueditasModel rueditasModel = new RueditasModel("00", "", "","", 0, 0.0, "");
        model.addAttribute("rueditasModel", rueditasModel);
        return "inicio";
    }
    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("placa") String placa,
                             Model model) {
        //Validar campos de entrada
        if (placa == null || placa.trim().length() == 0) {

            RueditasModel rueditasModel = new RueditasModel("01", "Error: Debe ingresar placa",
                    "","", 0, 0.0, "");
            model.addAttribute("rueditasModel", rueditasModel);
            return "inicio";
        }
        //Invocar API de validacion de usuario

        String endpoint = "http://localhost:8081/autenticacion/inicio";
        ConsultaRequestDTO consultaRequestDTO = new ConsultaRequestDTO(placa);
        ConsultaResponseDTO consultaResponseDTO = restTemplate.postForObject(endpoint, consultaRequestDTO, ConsultaResponseDTO.class);

        //Validar respuesta
        if (consultaResponseDTO != null && consultaResponseDTO.mensaje().equals("Consulta exitosa")) {
            RueditasModel rueditasModel = new RueditasModel(
                    consultaResponseDTO.placa(),
                    consultaResponseDTO.mensaje(),
                    consultaResponseDTO.marca(),
                    consultaResponseDTO.modelo(),
                    consultaResponseDTO.nroAsientos(),
                    consultaResponseDTO.precio(),
                    consultaResponseDTO.color()
            );
            model.addAttribute("rueditasModel", rueditasModel);
            return "buscar";
        } else {
            RueditasModel rueditasModel = new RueditasModel("02", "Debe ingresar una placa correcta", "", "", 0, 0.0, "");
            model.addAttribute("rueditasModel", rueditasModel);
            return "inicio";

        }
    }


}
