import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import repairpriceService from "../services/repairprice.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import { Select } from "@mui/material";

const AddRepairPrice = () => {
    const [type,setType] = useState("");
    const [montoG,setMontoG] = useState();
    const [montoD,setMontoD] = useState();
    const [montoH,setMontoH] = useState();
    const [montoE,setMontoE] = useState();
    
    const navigate = useNavigate();

    const saveRprice = (e) => {
        e.preventDefault();
    
        // Validación de campos vacíos
        if (!type || !montoG || !montoD || !montoH || !montoE) {
          alert("Por favor complete todos los campos.");
          return;
        }
    
        const rprice = { type, montoG, montoD, montoH, montoE };
        
        //Crear nuevo Rprice
        repairpriceService
        .create(rprice)
        .then((response) => {
            console.log("Precio de reparación ha sido añadido.", response.data);
            navigate("/rprice/list");
        })
        .catch((error) => {
            console.log(
            "Ha ocurrido un error al intentar crear nueva reparación.",
            error
            );
        });
    };

    useEffect(() => {
    }, []);
    
    return (
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          component="form"
          padding={7}
        >
          <h3> Añadir Precio de Reparación </h3>
    
          <form>
            <FormControl fullWidth>
              <TextField
                id="type"
                label="Tipo"
                value={type}
                variant="outlined"
                onChange={(e) => setType(e.target.value)}
                style={{ margin: "5px" }}
                helperText="Ej: Reparaciones del Motor"
              />
            </FormControl>
    
            <FormControl fullWidth>
              <TextField
                id="montog"
                label="Precio Gasolina"
                value={montoG}
                variant="outlined"
                onChange={(e) => setMontoG(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="montod"
                label="Precio Diésel"
                value={montoD}
                variant="outlined"
                onChange={(e) => setMontoD(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="montoh"
                label="Precio Híbrido"
                value={montoH}
                variant="outlined"
                onChange={(e) => setMontoH(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="montoe"
                label="Precio Eléctrico"
                value={montoE}
                variant="outlined"
                onChange={(e) => setMontoE(e.target.value)}
                style={{ margin: "5px" }}
              />
            </FormControl>
    
            <FormControl>
              <br />
              <Button
                variant="contained"
                color="info"
                onClick={(e) => saveRprice(e)}
                style={{ marginLeft: "0.5rem" }}
                startIcon={<SaveIcon />}
              >
                Guardar
              </Button>
            </FormControl>
          </form>
          <br />
          <Link to="/rprice/list">Back to List</Link>
        </Box>
      );

};

export default AddRepairPrice;