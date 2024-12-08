import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import NotFound from './components/NotFound';
import CarList from './components/CarList'
import AddEditCar from './components/AddEditCar';
import Prueba from './components/Prueba';
import RepairList from './components/RepairList';
import RepairDetails from './components/RepairDetails'
import AddRepair from './components/AddRepair';
import EditRepair from './components/EditRepair'
import Reporte2 from './components/Reporte2'
import BonoList from './components/BonoList'
import AddBono from './components/AddBono';
import RepairPriceList from './components/RepairPriceList';
import AddRepairPrice from './components/AddRepairPrice';
import EditRepairPrice from './components/EditRepairPrice';
import AllRepairs from './components/AllRepairs';
import Reporte1 from './components/Reporte1';
import GenReporte2 from './components/GenReporte2';

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="*" element={<NotFound/>} />

              <Route path="/car/list" element={<CarList/>} />
              <Route path="/car/add" element={<AddEditCar/>} />
              <Route path="/car/edit/:id" element={<AddEditCar/>} />

              <Route path="/repairs/:id" element={<RepairList/>} />
              <Route path="/repair/:id" element={<RepairDetails/>} />
              <Route path="/repair/add/:id" element={<AddRepair/>} />
              <Route path="/repair/edit/:id" element={<EditRepair/>} />

              <Route path="/rprice/list" element={<RepairPriceList/>} />
              <Route path="/rprice/add" element={<AddRepairPrice/>} />
              <Route path="/rprice/edit/:id" element={<EditRepairPrice/>} />

              <Route path="/bono/list" element={<BonoList/>} />
              <Route path="/bono/add" element={<AddBono/>} />

              <Route path="/allrepairs" element={<AllRepairs/>} />

              <Route path="/reporte1" element={<Reporte1/>} />
              <Route path="/reporte2" element={<Reporte2/>} />
              <Route path="/reporte2/:fecha" element={<GenReporte2/>} />

              <Route path="/prueba" element={<Prueba/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App
