import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import "../HostsPage/HostsPage.css";
import AccommodationsGrid from "../../components/accommodations/AccommodationsGrid/AccommodationsGrid.jsx";
import AddAccommodationDialog from "../../components/accommodations/AddAccommodationDialog/AddAccommodationDialog.jsx";
import useAccommodations from "../../../hooks/useAccommodations.js";
const AccommodationPage = () => {
    const {accommodations, loading, onAdd, onEdit, onDelete,onRent} = useAccommodations();
    const [addAccommodationDialogOpen, setAddAccommodationDialogOpen] = useState(false);
    const [filteredAccommodations,setFilteredAccommodations]=useState([]);
    const filterReserved= ()=>{
        setFilteredAccommodations(accommodations.filter((accommodation)=>accommodation.isReserved==true));

    }
    const clearFilter= ()=>{
        setFilteredAccommodations([]);
    }
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
                        <Box sx={{display: "flex", justifyContent: "space-between", mb: 2}}>
                            <Button variant="contained" color="primary" onClick={filteredAccommodations.length === 0 ? filterReserved : clearFilter}>
                                {filteredAccommodations.length === 0 ? "Show Reserved" : "Show All"}
                            </Button>
                            <Button variant="contained" color="primary" onClick={() => setAddAccommodationDialogOpen(true)}>
                                Add Accommodation
                            </Button>

                        </Box>
                        {filteredAccommodations.length==0 &&
                            <AccommodationsGrid accommodations={accommodations} onRent={onRent} onEdit={onEdit} onDelete={onDelete}/>
                        }
                        {filteredAccommodations.length>0 &&
                            <AccommodationsGrid accommodations={filteredAccommodations} onRent={onRent} onEdit={onEdit} onDelete={onDelete}/>
                        }
                    </>}
            </Box>
            <AddAccommodationDialog
                open={addAccommodationDialogOpen}
                onClose={() => setAddAccommodationDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};

export default AccommodationPage;