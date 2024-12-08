import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
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

const GenReporte2 = () => {
    const { fecha } = useParams();
    const [reports, setReports] = useState([]);
    const navigate = useNavigate();
    
    useEffect(() => {
        init();
    }, []);

    const init = () => {
        reportService
            .report2(fecha)
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
                Comparación de Reparaciones en 3 diferentes meses
            </Typography>

            <TableContainer sx={{ width: "90%", mb: "2rem" }} component={Paper}>
                <Table size="small" aria-label="a dense table">
                    <TableHead sx={{ backgroundColor: "#632654" }}>
                        <TableRow>
                            <TableCell align="left" sx={{ fontWeight: "bold", color: "white" }}>MES</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>{reports[0] ? reports[0].month1Name : "-"}</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>%Variacion</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>{reports[0] ? reports[0].month2Name : "-"}</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>%Variacion</TableCell>
                            <TableCell align="right" sx={{ fontWeight: "bold", color: "white" }}>{reports[0] ? reports[0].month3Name : "-"}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {reports.map((report) => (
                            <React.Fragment key={report.repairType}>
                                <TableRow sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                                    <TableCell align="left" sx={{ fontWeight: "bold" }}>{report.repairType}</TableCell>
                                    <TableCell align="right">{report.month1Count}</TableCell>
                                    <TableCell align="right">{report.month1_2CountVar}%</TableCell>
                                    <TableCell align="right">{report.month2Count}</TableCell>
                                    <TableCell align="right">{report.month2_3CountVar}%</TableCell>
                                    <TableCell align="right">{report.month3Count}</TableCell>
                                </TableRow>
                                <TableRow sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                                    <TableCell align="left"></TableCell>
                                    <TableCell align="right">{report.month1Monto ? "$" + report.month1Monto : "-"}</TableCell>
                                    <TableCell align="right">{report.month1_2MontoVar}%</TableCell>
                                    <TableCell align="right">{report.month2Monto ? "$" + report.month2Monto : "-"}</TableCell>
                                    <TableCell align="right">{report.month2_3MontoVar}%</TableCell>
                                    <TableCell align="right">{report.month3Monto ? "$" + report.month3Monto : "-"}</TableCell>
                                </TableRow>
                            </React.Fragment>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default GenReporte2;