import React from 'react';
import {Box, CircularProgress, Typography} from "@mui/material";
import ProductsGrid from "../../components/products/ProductsGrid/ProductsGrid.jsx";
import useProducts from "../../../hooks/useProducts.js";
import "./ProductsPage.css";
import {useParams} from "react-router";

const ProductsPage = () => {
    const {category} = useParams();
    const {products, loading} = useProducts(category);

    if(!category){
        return (
            <Box className="products-landing">
                <Box className="products-landing-panel">
                    <Typography variant="overline" className="products-landing-eyebrow">
                        E-SHOP
                    </Typography>
                    <Typography variant="h2" component="h1" className="products-landing-title">
                        Прегледај техника без непотребен хаос.
                    </Typography>
                    <Typography variant="body1" className="products-landing-description">
                        На едно место ги разгледуваш фрижидерите и инвертерите од повеќе продавници,
                        со детали и споредба што се чита лесно.
                    </Typography>
                    <Typography variant="body2" className="products-landing-hint">
                        Избери категорија од горното мени за да продолжиш.
                    </Typography>
                </Box>
            </Box>
        );
    }
    else{
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
                            <ProductsGrid products={products}/>
                        </>}
                </Box>
            </>
        );
    }

};

export default ProductsPage;
