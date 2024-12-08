import { useState, useEffect } from "react";
import { Link, useParams, useNavigate, Form } from "react-router-dom";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import { Select } from "@mui/material";
import Typography from "@mui/material/Typography";
import Chip from "@mui/material/Chip";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";

const Reporte2 = () => {
    const [fechax, setFechax] = useState("");
    const navigate = useNavigate();

    const formatFecha = (fecha) => {
        const [year, month] = fecha.split("-");
        return `${month}-${year}`;
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Validación de campos vacíos
        if (!fechax) {
            alert("Por favor complete todos los campos.");
            return;
        }

        const fecha = formatFecha(fechax);
        navigate(`/reporte2/${fecha}`);  
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
            <h3> Generar Reporte 2 </h3>
            <div>
                

                <FormControl fullWidth>
                    <TextField
                        id="fecha"
                        label="Fecha"
                        type="month"
                        value={fechax}
                        onChange={(e) => setFechax(e.target.value)}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <FormControl>
                    <br />
                    <Button
                        variant="contained"
                        color="info"
                        onClick={(e) => handleSubmit(e)}
                        style={{ marginLeft: "0.5rem" }}
                        startIcon={<SaveIcon />}
                    >
                        Generar Reporte 2
                    </Button>
                </FormControl>
            </div>
        </Box>
    );
};

export default Reporte2;