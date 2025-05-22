import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import "../HostsPage/HostsPage.css";
import AccommodationsGrid from "../../components/accommodations/AccommodationsGrid/AccommodationsGrid.jsx";
import AddAccommodationDialog from "../../components/accommodations/AddAccommodationDialog/AddAccommodationDialog.jsx";
import useAccommodations from "../../../hooks/useAccommodations.js";
const AccommodationPage = () => {
    const {accommodations, loading, onAdd, onEdit, onDelete} = useAccommodations();
    const [addAccommodationDialogOpen, setAddAccommodationDialogOpen] = useState(false);

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
                            <Button variant="contained" color="primary" onClick={() => setAddAccommodationDialogOpen(true)}>
                                Add Accommodation
                            </Button>
                        </Box>
                        <AccommodationsGrid accommodations={accommodations} onEdit={onEdit} onDelete={onDelete}/>
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