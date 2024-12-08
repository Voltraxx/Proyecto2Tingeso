import { useState, useEffect } from "react";
import { Link, useParams, useNavigate, Form } from "react-router-dom";
import repairService from "../services/repair.service";
import carService from "../services/car.service";
import bonoService from "../services/bono.service";
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

const AddRepair = () => {
    const { id } = useParams();
    const [car,setCar] = useState([]);
    const [bonos,setBonos] = useState([]);
    const [selBono, setSelBono] = useState(0);
    const [repairs,setRepairs] = useState([]);
    const [fechaIngresox, setFechaIngresox] = useState("");
    const [horaIngreso, setHoraIngreso] = useState("");

    const navigate = useNavigate();

    const formatFecha = (fecha) => {
        const [year, month, day] = fecha.split("-");
        return `${day}/${month}/${year}`;
    };

    const idCar = id;
    console.log("El idCar es:", idCar);

    const init = async () => {
        try {
          const response = await carService.get(id);
          console.log("Mostrando auto.", response.data);
          setCar(response.data);
          getBonos(response.data.marca); 
        } catch (error) {
          console.log(
            "Se ha producido un error.",
            error
          );
        }
      };

    const getBonos = (marca) => {
          bonoService
          .getBonosByMarca(marca)
          .then((response) => {
            console.log("Mostrando bonos.", response.data);
            setBonos(response.data);
          })
          .catch((error) => {
            console.log(
              "Se ha producido un error al intentar mostrar los bonos.",
              error
            );
          });
    };

    const saveRepair = (e) => {
        e.preventDefault();

        // Validación de campos vacíos
        if (!repairs || !fechaIngresox || !horaIngreso) {
            alert("Por favor complete todos los campos.");
            return;
        }

        var bonusDiscount = 0;

        if (selBono != 0) {
            bonusDiscount = selBono.monto;
            bonoService
            .remove(selBono.id)
            .then((response) => {
                console.log("Se eliminó el bono.", response.data);
                setBonos(response.data);
              })
              .catch((error) => {
                console.log(
                  "Se ha producido un error al intentar eliminar el bono.",
                  error
                );
              });
        };

        const fechaIngreso = formatFecha(fechaIngresox);
        const repair = {idCar, repairs, fechaIngreso, horaIngreso, bonusDiscount};

        //Crear nueva reparación
        repairService
            .create(repair)
            .then((response) => {
                console.log("Reparación ha sido añadida.", response.data);
                navigate(`/repairs/${id}`);
            })
            .catch((error) => {
                console.log(
                    "Ha ocurrido un error al intentar crear nueva reparación.",
                    error
                );
            });
    };

    // const handleAddRepair = (newType) => {
    //     if (repairs.includes(newType)) {
    //         alert("La reparación ya ha sido añadida.");
    //     } else {
    //         setRepairs([...repairs, newType]);
    //     }
    // };

    // const handleRemoveRepair = (typeToRemove) => {
    //     setRepairs(repairs.filter(type => type !== typeToRemove));
    // };

    const handleToggleRepair = (repair) => {
        if (repairs.includes(repair)) {
            setRepairs(repairs.filter((r) => r !== repair));
        } else {
            setRepairs([...repairs, repair]);
        }
    };

    useEffect(() => {  
        init();
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
            <h3> Añadir Reparación </h3>
            <div>
                

                <FormControl fullWidth>
                    <TextField
                        id="fecha"
                        label="Fecha"
                        type="date"
                        value={fechaIngresox}
                        onChange={(e) => setFechaIngresox(e.target.value)}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="hora"
                        label="Hora"
                        type="time"
                        value={horaIngreso}
                        onChange={(e) => setHoraIngreso(e.target.value)}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        inputProps={{
                          step: 300, // 5 minutos
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <Box>
                    <FormControl component="fieldset">
                        <FormGroup>
                            <Typography sx={{ mt:"1rem", mb:"1rem",fontWeight: "bold"}}>Seleccionar Reparaciones:</Typography>
                            {[
                                "Reparaciones del Sistema de Frenos",
                                "Servicio del Sistema de Refrigeración",
                                "Reparaciones del Motor",
                                "Reparaciones de la Transmisión",
                                "Reparación del Sistema Eléctrico",
                                "Reparaciones del Sistema de Escape",
                                "Reparación de Neumáticos y Ruedas",
                                "Reparaciones de la Suspensión y la Dirección",
                                "Reparación del Sistema de Aire Acondicionado y Calefacción",
                                "Reparaciones del Sistema de Combustible",
                                "Reparación y Reemplazo del Parabrisas y Cristales",
                            ].map((repair, index) => (
                                <FormControlLabel
                                    key={index}
                                    control={<Checkbox checked={repairs.includes(repair)} onChange={() => handleToggleRepair(repair)} />}
                                    label={repair}
                                />
                            ))}
                        </FormGroup>
                    </FormControl>
                </Box>

                {bonos.length > 0 ? (
                    <FormControl fullWidth>
                        <TextField
                            id="bono"
                            label="Asignar bono"
                            value={selBono}
                            onChange={(e) => setSelBono(e.target.value)}
                            variant="outlined"
                            defaultValue="Ninguno"
                            select
                            style={{ margin: "5px" }}
                        >
                            <MenuItem value={0}>Ninguno</MenuItem>
                            {bonos.length > 0 && bonos.map((bono) => (
                            <MenuItem key={bono.id} value={bono}>
                                Bono de ${bono.monto}
                            </MenuItem>
                            ))}
                        </TextField>
                    </FormControl>
                ): ""}

                <FormControl>
                    <br />
                    <Button
                        variant="contained"
                        color="info"
                        onClick={(e) => saveRepair(e)}
                        style={{ marginLeft: "0.5rem" }}
                        startIcon={<SaveIcon />}
                    >
                        Registrar Reparación
                    </Button>
                </FormControl>
            </div>
            <br />
            <Link to="/car/list">Back to List</Link>
        </Box>
    );
};

export default AddRepair;
