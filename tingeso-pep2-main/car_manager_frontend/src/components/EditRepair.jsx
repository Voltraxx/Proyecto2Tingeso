import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import repairService from "../services/repair.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";

const EditRepair = () => {
    const { id } = useParams();
    const [idCar, setIdCar] = useState();
    const [fechaIngresox, setFechaIngresox] = useState("");
    const [horaIngreso, setHoraIngreso] = useState("");
    const [montoTotal, setMontoTotal] = useState();
    const [fechaSalidax, setFechaSalidax] = useState("");
    const [horaSalida, setHoraSalida] = useState("");
    const [fechaDespachox, setFechaDespachox] = useState("");
    const [horaDespacho, setHoraDespacho] = useState("");
    const [repairDiscount, setRepairDiscount] = useState();
    const [dayDiscount, setDayDiscount] = useState();
    const [bonusDiscount, setBonusDiscount] = useState();
    const [kmCharge, setKmCharge] = useState();
    const [ageCharge, setAgeCharge] = useState();
    const [delayCharge, setDelayCharge] = useState();
    const [ivaCharge, setIvaCharge] = useState();
    const [repairDays, setRepairDays] = useState();
    const [status, setStatus] = useState("");

    const navigate = useNavigate();

    const formatFecha = (fecha) => {
        if (fecha == null) {
            return fecha;
        } else if (!fecha.includes("-")) {
            return fecha;
        }
        const [year, month, day] = fecha.split("-");
        return `${day}/${month}/${year}`;
    };

    const reverseFormatFecha = (fecha) => {
        if (fecha == null) {
            return fecha;
        } else if (!fecha.includes("/")) {
            return fecha;
        }
        const [day, month, year] = fecha.split("/");
        return `${year}-${month}-${day}`;
    };

    const saveRepair = (e) => {
        e.preventDefault();

        const fechaIngreso = formatFecha(fechaIngresox);
        const fechaSalida = formatFecha(fechaSalidax);
        const fechaDespacho = formatFecha(fechaDespachox);

        const repair = {id, 
                        idCar, 
                        fechaIngreso, 
                        horaIngreso,
                        montoTotal,
                        fechaSalida,
                        horaSalida,
                        fechaDespacho,
                        horaDespacho,
                        repairDiscount,
                        dayDiscount,
                        bonusDiscount,
                        kmCharge,
                        ageCharge,
                        delayCharge,
                        ivaCharge,
                        repairDays,
                        status
                        };

        //Actualizar Datos de Reparación
        repairService
        .update(repair)
        .then((response) => {
            console.log("Reparación ha sido actualizado.", response.data);
            navigate(`/repairs/${idCar}`);
        })
        .catch((error) => {
            console.log(
            "Ha ocurrido un error al intentar actualizar datos de la reparación.",
            error
            );
        });

    };

    useEffect(() => {
        repairService
        .get(id)
        .then((repair) => {
            setIdCar(repair.data.idCar);
            setFechaIngresox(reverseFormatFecha(repair.data.fechaIngreso));
            setHoraIngreso(repair.data.horaIngreso);
            setMontoTotal(repair.data.montoTotal);
            setFechaSalidax(reverseFormatFecha(repair.data.fechaSalida));
            setHoraSalida(repair.data.horaSalida);
            setFechaDespachox(reverseFormatFecha(repair.data.fechaDespacho));
            setHoraDespacho(repair.data.horaDespacho);
            setRepairDiscount(repair.data.repairDiscount);
            setDayDiscount(repair.data.dayDiscount);
            setBonusDiscount(repair.data.bonusDiscount);
            setKmCharge(repair.data.kmCharge);
            setAgeCharge(repair.data.ageCharge);
            setDelayCharge(repair.data.delayCharge);
            setIvaCharge(repair.data.ivaCharge);
            setRepairDays(repair.data.repairDays);
            setStatus(repair.data.status);
        })
        .catch((error) => {
            console.log("Se ha producido un error.", error);
        });
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
            <h3> Editar Reparación </h3>
            <form>
            
                <FormControl fullWidth>
                    <TextField
                        id="fecha1"
                        label="Fecha Ingreso"
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
                        id="hora1"
                        label="Hora Ingreso"
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

                <FormControl fullWidth>
                    <TextField
                        id="fecha2"
                        label="Fecha Salida"
                        type="date"
                        value={fechaSalidax}
                        onChange={(e) => setFechaSalidax(e.target.value)}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="hora2"
                        label="Hora Salida"
                        type="time"
                        value={horaSalida}
                        onChange={(e) => setHoraSalida(e.target.value)}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        inputProps={{
                          step: 300, // 5 minutos
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="fecha3"
                        label="Fecha Despacho"
                        type="date"
                        value={fechaDespachox}
                        onChange={(e) => setFechaDespachox(e.target.value)}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <FormControl fullWidth>
                    <TextField
                        id="hora3"
                        label="Hora Despacho"
                        type="time"
                        value={horaDespacho}
                        onChange={(e) => setHoraDespacho(e.target.value)}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        inputProps={{
                          step: 300, // 5 minutos
                        }}
                        style={{ margin: "5px" }}
                    />
                </FormControl>

                <FormControl>
                    <br />
                    <Button
                        variant="contained"
                        color="info"
                        onClick={(e) => saveRepair(e)}
                        style={{ marginLeft: "0.5rem" }}
                        startIcon={<SaveIcon />}
                    >
                        Guardar Cambios
                    </Button>
                </FormControl>
            </form>
            <br />
            <Link to="/car/list">Back to List</Link>
        </Box>
    );
};

export default EditRepair;
