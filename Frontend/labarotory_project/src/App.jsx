import AccommodationPage from  "./ui/pages/AccommodationPage/AccommodationPage.jsx";
import HostsPage from "./ui/pages/HostsPage/HostsPage.jsx";
import CountriesPage from "./ui/pages/CountriesPage/CountriesPage.jsx";
import {BrowserRouter, Routes, Route} from "react-router";
import Layout from "./ui/components/layout/Layout/Layout.jsx";
import HomePage from "./ui/pages/HomePage.jsx";
import Register from "./ui/components/auth/Register.jsx";
import Login from "./ui/components/auth/Login.jsx"
import ProtectedRoute from "./ui/components/routing/ProtectedRoute/ProtectedRoute.jsx";
import './App.css'

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/register" element={<Register/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<HomePage/>}/>
                    <Route element={<ProtectedRoute/>}>
                        <Route path="accommodations" element={<AccommodationPage/>}/>
                        <Route path="hosts" element={<HostsPage/>}/>
                        <Route path="countries" element={<CountriesPage/>}/>
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    );
};

export default App
