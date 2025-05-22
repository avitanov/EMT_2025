import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import "../HostsPage/HostsPage.css";
import CountriesGrid from "../../components/countries/CountriesGrid/CountriesGrid.jsx";
import useCountry from "../../../hooks/useCountry.js";
import AddCountryDialog from "../../components/countries/AddCountryDialog/AddCountryDialog.jsx";
const CountriesPage = () => {
    const {countries, loading, onAdd, onEdit, onDelete} = useCountry();
    const [addCountryDialogOpen, setAddCountryDialogOpen] = useState(false);

    return (
        <>
            <Box className="products-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button variant="contained" color="primary" onClick={() => setAddCountryDialogOpen(true)}>
                                Add Country
                            </Button>
                        </Box>
                        <CountriesGrid countries={countries} onEdit={onEdit} onDelete={onDelete}/>
                    </>}
            </Box>
            <AddCountryDialog
                open={addCountryDialogOpen}
                onClose={() => setAddCountryDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};

export default CountriesPage;