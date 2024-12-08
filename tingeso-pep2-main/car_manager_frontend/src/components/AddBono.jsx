import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import bonoService from "../services/bono.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";

const AddBono = () => {
  const [marca, setMarca] = useState("");
  const [monto, setMonto] = useState();
  const navigate = useNavigate();

  const saveBono = (e) => {
    e.preventDefault();

    // Validación de campos vacíos
    if (!marca || !monto) {
      alert("Por favor complete todos los campos.");
      return;
    }

    const bono = {marca, monto};
    //Crear nuevo bono
    bonoService
    .create(bono)
    .then((response) => {
        console.log("Bono ha sido añadido.", response.data);
        navigate("/bono/list");
    })
    .catch((error) => {
        console.log(
        "Ha ocurrido un error al intentar crear nuevo bono.",
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
      <h3> Añadir Bono </h3>

      <form>
        <FormControl fullWidth>
          <TextField
            id="marca"
            label="Marca"
            value={marca}
            variant="outlined"
            onChange={(e) => setMarca(e.target.value)}
            style={{ margin: "5px" }}
            helperText="Ej: Suzuki,Chevrolet,etc"
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="monto"
            label="Monto"
            value={monto}
            variant="outlined"
            onChange={(e) => setMonto(e.target.value)}
            style={{ margin: "5px" }}
          />
        </FormControl>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => saveBono(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <br />
      <Link to="/bono/list">Back to List</Link>
    </Box>
  );
};

export default AddBono;