import React from 'react';
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";

const DeleteAccommodationDialog = ({open, onClose, accommodation, onDelete}) => {

    const handleSubmit = () => {
        onDelete(accommodation.id);
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Delete Accommodation</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to delete <strong>{accommodation.name} - {accommodation.host.name}</strong>? This action cannot be undone.
                </DialogContentText>
                <DialogActions>
                    <Button onClick={onClose}>Cancel</Button>
                    <Button onClick={handleSubmit} color="error" variant="contained">Delete</Button>
                </DialogActions>
            </DialogContent>
        </Dialog>
    );
};

export default DeleteAccommodationDialog;