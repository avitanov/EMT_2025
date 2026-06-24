// src/hooks/useProductDetails.js
import {useCallback, useEffect, useState} from "react";
import productRepository from "../repository/productRepository.js";

const asProductList = (data) => {
    if (Array.isArray(data)) {
        return data;
    }

    console.warn("Expected similar products response to be an array.", data);
    return [];
};

const normalizeProductDetails = (data) => {
    if (!data || typeof data !== "object") {
        return null;
    }

    return {
        ...data,
        priceMkd: data.priceMkd ?? data.price,
        imageUrl: data.imageUrl ?? data.ImageUrl,
        specifications: Array.isArray(data.specifications)
            ? data.specifications
            : Array.isArray(data.specificationList)
                ? data.specificationList
                : [],
    };
};

const useProductDetails = (id, category) => {
    const [state, setState] = useState({
        "product": null,
        "similarProducts": [],
        "isLoadingSimilar": false, // New state for loading similar products
        "hasFetchedSimilar": false // New state to track if similar products have been fetched
    });

    // Fetches the main product details when the ID or category changes
    useEffect(() => {
        if (category === "frizideri") {
            productRepository
                .findFriziderById(id)
                .then((response) => {
                    // Reset similarProducts and related states when main product changes
                    setState(prevState => ({
                        ...prevState,
                        "product": normalizeProductDetails(response.data),
                        "similarProducts": [],
                        "isLoadingSimilar": false,
                        "hasFetchedSimilar": false
                    }));
                })
                .catch((error) => console.log(error));
        } else {
            productRepository
                .findInverterById(id)
                .then((response) => {
                    // Reset similarProducts and related states when main product changes
                    setState(prevState => ({
                        ...prevState,
                        "product": normalizeProductDetails(response.data),
                        "similarProducts": [],
                        "isLoadingSimilar": false,
                        "hasFetchedSimilar": false
                    }));
                })
                .catch((error) => console.log(error));
        }
    }, [id, category]);

    // Callback to fetch similar products, to be called when a button is clicked
    const fetchSimilar = useCallback(() => {
        setState(prevState => ({...prevState, "isLoadingSimilar": true, "hasFetchedSimilar": false})); // Set loading to true
        if (category === "frizideri") {
            productRepository
                .findSimilarFrizider(id)
                .then((response) => {
                    setState(prevState => ({
                        ...prevState,
                        "similarProducts": asProductList(response.data),
                        "isLoadingSimilar": false, // Set loading to false
                        "hasFetchedSimilar": true // Mark as fetched
                    }));
                })
                .catch((error) => {
                    console.log(error);
                    setState(prevState => ({
                        ...prevState,
                        "isLoadingSimilar": false, // Set loading to false
                        "hasFetchedSimilar": true, // Mark as fetched even on error
                        "similarProducts": [] // Ensure empty array on error
                    }));
                });
        } else {
            productRepository
                .findSimilarInverter(id)
                .then((response) => {
                    setState(prevState => ({
                        ...prevState,
                        "similarProducts": asProductList(response.data),
                        "isLoadingSimilar": false, // Set loading to false
                        "hasFetchedSimilar": true // Mark as fetched
                    }));
                })
                .catch((error) => {
                    console.log(error);
                    setState(prevState => ({
                        ...prevState,
                        "isLoadingSimilar": false, // Set loading to false
                        "hasFetchedSimilar": true, // Mark as fetched even on error
                        "similarProducts": [] // Ensure empty array on error
                    }));
                });
        }
    }, [id, category]);

    return {...state, fetchSimilar: fetchSimilar};
};

export default useProductDetails;
