import { useState } from "react";
import { Button, TextField, FormControl, Box} from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";

const Prueba = () => {
  const [hora, setHora] = useState("");
  const [fecha, setFecha] = useState("");

  const handleSave = () => {
    if (hora && fecha) {
      const fechaFormateada = formatFecha(fecha);
      console.log("Fecha seleccionada:", fechaFormateada);
      console.log("Hora seleccionada:", hora);
      // Aquí puedes enviar fechaFormateada y hora al backend
    } else {
      console.log("Debe seleccionar una fecha y una hora");
    }
  };

  const formatFecha = (fecha) => {
    const [year, month, day] = fecha.split("-");
    return `${day}/${month}/${year.slice(2)}`;
  };

  return (
    <Box mt="3rem">

    <FormControl fullWidth>
      <TextField
        id="fecha"
        label="Fecha"
        type="date"
        value={fecha}
        onChange={(e) => setFecha(e.target.value)}
        InputLabelProps={{
          shrink: true,
        }}
      />
      <br />
      <TextField
        id="hora"
        label="Hora"
        type="time"
        value={hora}
        onChange={(e) => setHora(e.target.value)}
        InputLabelProps={{
          shrink: true,
        }}
        inputProps={{
          step: 300, // 5 minutos
        }}
      />
      <br />
      <TextField
        label="Fecha seleccionada"
        value={formatFecha(fecha)}
        disabled
      />
      <br />
      <TextField
        label="Hora seleccionada"
        value={hora}
        disabled
      />
      <br />
      <Button
        variant="contained"
        color="primary"
        onClick={handleSave}
        startIcon={<SaveIcon />}
      >
        Guardar
      </Button>
    </FormControl>

    </Box>
  );
};

export default Prueba;