import React from 'react';
import AccommodationCard from "../AccommodationCard/AccommodationCard.jsx";
import {Grid} from "@mui/material";

const AccommodationsGrid = ({accommodations, onEdit, onDelete}) => {
    return (
        <Grid container spacing={{xs: 2, md: 3}}>
            {accommodations.map((accommodation) => (
                <Grid key={accommodation.id} size={{xs: 12, sm: 6, md: 4, lg: 3}}>
                    <AccommodationCard accommodation={accommodation} onEdit={onEdit} onDelete={onDelete}/>
                </Grid>
            ))}
        </Grid>
    );
};

export default AccommodationsGrid;