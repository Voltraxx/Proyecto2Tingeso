import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import carService from '../services/car.service'
import repairService from "../services/repair.service";
import Box from '@mui/material/Box'
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import { Typography } from "@mui/material";
import Paper from "@mui/material/Paper";

const AllRepairs = () => {
    const [cars, setCars] = useState([]);
    const [repairs, setRepairs] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        init();
    }, []);

    const init = () => {
        carService
            .getAll()
            .then((response) => {
                console.log("Mostrando listado de todos los autos.", response.data);
                setCars(response.data);
            })
            .catch((error) => {
                console.log("Se ha producido un error al intentar mostrar listado de todos los autos.", error);
            });

        repairService
            .getAll()
            .then((response2) => {
                console.log("Mostrando listado de todos las reparaciones.", response2.data);
                setRepairs(response2.data);
            })
            .catch((error) => {
                console.log("Se ha producido un error al intentar mostrar listado de todas las reparaciones.", error);
            });
    };

    const getCarById = (idCar) => {
        return cars.find(car => car.id === idCar) || {};
    };

    return (
        <Box align="center" mt="5rem">
            <Typography sx={{ fontWeight: "bold" }} mb="3rem" variant="h4" gutterBottom>
                Todas las reparaciones
            </Typography>

            <TableContainer sx={{ minWidth: 650, mb: "2rem" }} component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="left" sx={{ fontWeight: "bold" }}>Patente Vehículo</TableCell>
                            <TableCell align="left" sx={{ fontWeight: "bold" }}>Marca Vehículo</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Modelo Vehículo</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Tipo Vehículo</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Año Fabricación</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Tipo Motor</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Fecha Ingreso</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Hora Ingreso</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Monto Total Reparaciones</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Monto Recargos</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Monto Dctos</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>SUB Total</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Monto IVA</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Costo Total</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Fecha Salida</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Hora Salida</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Fecha Retiro</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold" }}>Hora Retiro</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {repairs.map((repair) => {
                            const car = getCarById(repair.idCar);
                            return (
                                <TableRow key={repair.id} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                                    <TableCell align="left">{car.patente}</TableCell>
                                    <TableCell align="left">{car.marca}</TableCell>
                                    <TableCell align="right">{car.modelo}</TableCell>
                                    <TableCell align="right">{car.type}</TableCell>
                                    <TableCell align="right">{car.year}</TableCell>
                                    <TableCell align="right">{car.motor}</TableCell>
                                    <TableCell align="right">{repair.fechaIngreso}</TableCell>
                                    <TableCell align="right">{repair.horaIngreso}</TableCell>
                                    <TableCell align="right">${repair.montoTotal + repair.repairDiscount + repair.dayDiscount + repair.bonusDiscount - repair.kmCharge - repair.ageCharge - repair.delayCharge - repair.ivaCharge}</TableCell>
                                    <TableCell align="right">${repair.kmCharge + repair.ageCharge + repair.delayCharge}</TableCell>
                                    <TableCell align="right">${repair.repairDiscount + repair.dayDiscount + repair.bonusDiscount}</TableCell>
                                    <TableCell align="right">${repair.montoTotal - repair.ivaCharge}</TableCell>
                                    <TableCell align="right">${repair.ivaCharge}</TableCell>
                                    <TableCell align="right">${repair.montoTotal}</TableCell>
                                    <TableCell align="right">{repair.fechaSalida ? repair.fechaSalida : "Pendiente" }</TableCell>
                                    <TableCell align="right">{repair.horaSalida ? repair.horaSalida : "Pendiente" }</TableCell>
                                    <TableCell align="right">{repair.fechaDespacho ? repair.fechaDespacho : "Pendiente" }</TableCell>
                                    <TableCell align="right">{repair.horaDespacho ? repair.horaDespacho : "Pendiente" }</TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default AllRepairs;