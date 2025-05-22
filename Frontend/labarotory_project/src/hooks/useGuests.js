import {useCallback, useEffect, useState} from "react";
import guestRepository from "../repository/guestRepository.js";
import accommodationsRepository from "../repository/accommodationsRepository.js";


const initialState = {
    "guests": [],
    "loading": true,
};

const useGuests = ()=>{
    const [state,setState]=useState(initialState)

    const fetchGuests = useCallback(()=>{
        setState(initialState);
        guestRepository
            .findAll()
            .then((response) => {
                setState({
                    "guests": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));

    },[]);

    const onAdd = useCallback((data) => {
        guestRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new guest.");
                fetchGuests();
            })
            .catch((error) => console.log(error));
    }, [fetchGuests]);

    const onEdit = useCallback((id, data) => {
        guestRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the product with ID ${id}.`);
                fetchGuests();
            })
            .catch((error) => console.log(error));
    }, [fetchGuests]);

    const onDelete = useCallback((id) => {
        guestRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the product with ID ${id}.`);
                fetchGuests();
            })
            .catch((error) => console.log(error));
    }, [fetchGuests]);



    useEffect(() => {
        fetchGuests()
    }, [fetchGuests]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete};
}

export default useGuests;