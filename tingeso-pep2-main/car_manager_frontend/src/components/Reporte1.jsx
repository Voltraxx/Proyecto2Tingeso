import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import reportService from '../services/report.service';
import Box from '@mui/material/Box';
import { Typography } from "@mui/material";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

const Reporte1 = () => {
    const [reports, setReports] = useState([]);
    const navigate = useNavigate();
    
    useEffect(() => {
        init();
    }, []);

    const init = () => {
        reportService
            .report1()
            .then((response) => {
                console.log("Mostrando listado de todos los reportes.", response.data);
                setReports(response.data);
            })
            .catch((error) => {
                console.log(
                    "Se ha producido un error al intentar mostrar listado de todos los reportes.",
                    error
                );
            });
    };

    return (
        <Box align="center" mt="5rem">
            <Typography sx={{ fontWeight: "bold" }} mb="3rem" variant="h4" gutterBottom>
                Tipos de Reparaciones vs Tipos de Vehículos
            </Typography>

            <TableContainer sx={{ width: "90%", mb: "2rem" }} component={Paper}>
                <Table  size="small" aria-label="a dense table">
                    <TableHead sx={{backgroundColor:"#632654"}}>
                        <TableRow>
                            <TableCell align="left" sx={{ fontWeight: "bold", color: "white" }}>Lista de Reparaciones</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>Sedan</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>Hatchback</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>SUV</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>Pickup</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>Furgoneta</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>Total</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {reports.map((report) => (
                            <>
                                <TableRow key={`${report.repairType}-count`} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                                    <TableCell align="left" sx={{ fontWeight: "bold"}}>{report.repairType}</TableCell>
                                    <TableCell align="right">{report.sedanCount}</TableCell>
                                    <TableCell align="right">{report.hatchbackCount}</TableCell>
                                    <TableCell align="right">{report.suvCount}</TableCell>
                                    <TableCell align="right">{report.pickupCount}</TableCell>
                                    <TableCell align="right">{report.furgoCount}</TableCell>
                                    <TableCell align="right">{report.sedanCount + report.hatchbackCount + report.suvCount + report.pickupCount + report.furgoCount}</TableCell>
                                </TableRow>
                                <TableRow key={`${report.repairType}-monto`} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                                    <TableCell align="left"></TableCell>
                                    <TableCell align="right">{report.sedanMonto ? "$" + report.sedanMonto : "-"}</TableCell>
                                    <TableCell align="right">{report.hatchbackMonto ? "$" + report.hatchbackMonto : "-"}</TableCell>
                                    <TableCell align="right">{report.suvMonto ? "$" + report.suvMonto : "-"}</TableCell>
                                    <TableCell align="right">{report.pickupMonto ? "$" + report.pickupMonto : "-"}</TableCell>
                                    <TableCell align="right">{report.furgoMonto ? "$" + report.furgoMonto : "-" }</TableCell>
                                    <TableCell align="right">${report.sedanMonto + report.hatchbackMonto + report.suvMonto + report.pickupMonto + report.furgoMonto}</TableCell>
                                </TableRow>
                            </>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default Reporte1;