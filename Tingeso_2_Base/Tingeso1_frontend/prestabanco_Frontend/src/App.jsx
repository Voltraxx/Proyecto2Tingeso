import './App.css';
import { HashRouter as Router, Route, Routes } from 'react-router-dom';
import CreditCalculator from './components/CreditCalculator';
import Home from './components/Home';
import Navbar from './components/Navbar';
import AddEditUser from './components/AddEditUser';
import UserList from './components/UserList';
import CreditRequestList from './components/CreditRequestList';
import AddEditCreditRequest from './components/AddEditCreditRequest';
import EvaluateCreditRequest from './components/EvaluateCreditRequest';


function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/" element={<Home/>} />
              <Route path="/UserList/" element={<UserList/>} />
              <Route path="/user/edit/:id" element={<AddEditUser/>} />
              <Route path="/user/add" element={<AddEditUser/>} />
              <Route path="/CreditCalculator" element={<CreditCalculator/>} />
              <Route path="/CreditRequestList/" element={<CreditRequestList/>} />
              <Route path="/creditRequest/add" element={<AddEditCreditRequest/>} />
              <Route path="/creditRequest/edit/:id" element={<AddEditCreditRequest/>} />
              <Route path="/creditRequest/evaluate/:id" element={<EvaluateCreditRequest/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App;