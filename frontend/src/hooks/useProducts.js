import {useCallback, useEffect, useState} from "react";
import productRepository from "../repository/productRepository.js";
const initialState = {
    "products": [],
    "loading": true,
};

const asProductList = (data) => {
    if (Array.isArray(data)) {
        return data;
    }

    console.warn("Expected products response to be an array.", data);
    return [];
};

const useProducts = (category) => {
    const [state, setState] = useState(initialState);

    const fetchFrizideri = useCallback(() => {
        setState(initialState);
        productRepository
            .findAllFrizideri()
            .then((response) => {
                setState({
                    "products": asProductList(response.data),
                    "loading": false,
                });
            })
            .catch((error) => {
                console.log(error);
                setState({
                    "products": [],
                    "loading": false,
                });
            });
    }, []);
    const fetchInverteri = useCallback(() => {
        setState(initialState);
        productRepository
            .findAllInverteri()
            .then((response) => {
                setState({
                    "products": asProductList(response.data),
                    "loading": false,
                });
            })
            .catch((error) => {
                console.log(error);
                setState({
                    "products": [],
                    "loading": false,
                });
            });
    }, []);


    useEffect(() => {
        if(category==="frizideri"){
            fetchFrizideri();
        }
        else if(category==="inverteri"){
            fetchInverteri()
        }
    }, [fetchFrizideri,fetchInverteri,category]);

    return {...state};
};

export default useProducts;
