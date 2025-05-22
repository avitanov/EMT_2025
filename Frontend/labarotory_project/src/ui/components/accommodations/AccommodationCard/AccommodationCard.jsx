import React, {useState} from 'react';
import InfoIcon from '@mui/icons-material/Info';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import {Box, Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import EditAccommodationDialog from "../EditAccommodationDialog/EditAccommodationDialog.jsx";
import DeleteAccommodationDialog from "../DeleteAccommodationDialog/DeleteAccommodationDialog.jsx";
import {useNavigate} from "react-router";

const AccommodationCard = ({accommodation, onEdit, onDelete, onRent}) => {
    const navigate = useNavigate();
    const [editAccommodationDialogOpen, setEditAccommodationDialogOpen] = useState(false);
    const [deleteAccommodationDialogOpen, setDeleteAccommodationDialogOpen] = useState(false);
    const handleSubmit = () => {
        onRent(accommodation.id);

    };


    return (
        <>
            <Card sx={{boxShadow: 3, borderRadius: 2, p: 1}} style={{width:290,}}>
                <CardContent>
                    <Typography variant="h5">{accommodation.name} </Typography>
                    <Typography variant="h5">Number of Rooms: {accommodation.numRooms} </Typography>
                    <Typography variant="h5">Host: {accommodation.host.name} - {accommodation.host.country.name} </Typography>
                    <Typography variant="h5" display="flex" alignItems="center" gap={1}>
                        {accommodation.isReserved ? (
                            <>
                                <CheckCircleIcon color="success" />
                                Reserved
                            </>
                        ) : (
                            <>
                                <CancelIcon color="error" />
                                Not Reserved
                            </>
                        )}
                    </Typography>



                </CardContent>
                <CardActions sx={{justifyContent: "space-between"}}>
                    <Button
                        size="small"
                        color="success"
                        startIcon={<InfoIcon/>}
                        onClick={handleSubmit}
                    >
                        Rent
                    </Button>
                    <Box>
                        <Button
                            size="small"
                            color="warning"
                            startIcon={<EditIcon/>}
                            sx={{mr: "0.25rem"}}
                            onClick={() => setEditAccommodationDialogOpen(true)}
                        >
                            Edit
                        </Button>
                        <Button
                            size="small"
                            color="error"
                            startIcon={<DeleteIcon/>}
                            onClick={() => setDeleteAccommodationDialogOpen(true)}
                        >
                            Delete
                        </Button>
                    </Box>
                </CardActions>
            </Card>
            <EditAccommodationDialog
                open={editAccommodationDialogOpen}
                onClose={() => setEditAccommodationDialogOpen(false)}
                accommodation={accommodation}
                onEdit={onEdit}
            />
            <DeleteAccommodationDialog
                open={deleteAccommodationDialogOpen}
                onClose={() => setDeleteAccommodationDialogOpen(false)}
                accommodation={accommodation}
                onDelete={onDelete}
            />
        </>
    );
};

export default AccommodationCard;