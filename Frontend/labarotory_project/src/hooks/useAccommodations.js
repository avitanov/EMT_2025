import {useCallback, useEffect, useState} from "react";
import accommodationsRepository from "../repository/accommodationsRepository.js";


const initialState = {
    "accommodations": [],
    "loading": true,
};


const useAccommodations = ()=>{
    const [state,setState]=useState(initialState)

    const fetchAccommodations= useCallback(()=>{
        setState(initialState);
        accommodationsRepository
            .findAll()
            .then((response) => {
                setState({
                    "accommodations": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));

    },[]);

    const onAdd = useCallback((data) => {
        accommodationsRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new accommodation.");
                fetchAccommodations();
            })
            .catch((error) => console.log(error));
    }, [fetchAccommodations]);

    const onEdit = useCallback((id, data) => {
        accommodationsRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the product with ID ${id}.`);
                fetchAccommodations();
            })
            .catch((error) => console.log(error));
    }, [fetchAccommodations]);

    const onDelete = useCallback((id) => {
        accommodationsRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the product with ID ${id}.`);
                fetchAccommodations();
            })
            .catch((error) => console.log(error));
    }, [fetchAccommodations]);



    useEffect(() => {
        fetchAccommodations()
    }, [fetchAccommodations]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete};
}

export default useAccommodations;