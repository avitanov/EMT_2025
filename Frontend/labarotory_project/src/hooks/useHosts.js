import {useCallback, useEffect, useState} from "react";
import hostRepository from "../repository/hostRepository.js";
import accommodationsRepository from "../repository/accommodationsRepository.js";

const initialState = {
    "hosts": [],
    "loading": true
}

const useHosts = () => {
    const [state, setState] = useState(initialState);

    const fetchHosts = useCallback(()=>{
        setState(initialState);
        hostRepository
            .findAll()
            .then((response) => {
                setState({
                    "hosts": response.data,
                    "loading": false,
                });
            })
            .catch((error) => console.log(error));

    },[]);

    const onAdd = useCallback((data) => {
        hostRepository
            .add(data)
            .then(() => {
                console.log("Successfully added a new host.");
                fetchHosts();
            })
            .catch((error) => console.log(error));
    }, [fetchHosts]);

    const onEdit = useCallback((id, data) => {
        hostRepository
            .edit(id, data)
            .then(() => {
                console.log(`Successfully edited the product with ID ${id}.`);
                fetchHosts();
            })
            .catch((error) => console.log(error));
    }, [fetchHosts]);

    const onDelete = useCallback((id) => {
        hostRepository
            .delete(id)
            .then(() => {
                console.log(`Successfully deleted the product with ID ${id}.`);
                fetchHosts();
            })
            .catch((error) => console.log(error));
    }, [fetchHosts]);



    useEffect(() => {
        fetchHosts()
    }, [fetchHosts]);

    return {...state, onAdd: onAdd, onEdit: onEdit, onDelete: onDelete};
};

export default useHosts;