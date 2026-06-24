// src/components/products/ProductDetails/ProductDetails.jsx
import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import useProductDetails from "../../../hooks/useProductDetails.js";
import {
    Box,
    Button,
    CircularProgress,
    Typography,
    Card,
    CardContent,
    CardMedia,
    Divider,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableRow
} from '@mui/material';
import { ArrowBack } from '@mui/icons-material';
import ProductsGrid from "../../components/products/ProductsGrid/ProductsGrid.jsx";

const fallbackImage = "/product-placeholder.svg";

const ProductDetails = () => {
    const { category, id } = useParams();
    // Destructure new state variables
    const { product, similarProducts, fetchSimilar, isLoadingSimilar, hasFetchedSimilar } = useProductDetails(id, category);
    const navigate = useNavigate();

    if (!product) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
                <CircularProgress />
            </Box>
        );
    }

    const { website, productName, specifications = [] } = product;
    const imageSrc = product.imageUrl ?? product.ImageUrl ?? fallbackImage;
    const priceValue = product.priceMkd ?? product.price;

    return (
        <Box>
            {/* Back link */}
            <Button
                startIcon={<ArrowBack />}
                onClick={() => navigate(`/products/${category}`)}
                sx={{ mb: 2 }}
            >
                Назад до {category}
            </Button>

            <Card sx={{ boxShadow: 3, borderRadius: 2 }}>
                <CardContent
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        gap: 3,
                        p: { xs: 2, md: 4 }
                    }}
                >
                    <Box sx={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
                        <Box
                            sx={{
                                width: { xs: 240, sm: 300, md: 360 },
                                height: { xs: 240, sm: 300, md: 360 },
                                maxWidth: '100%',
                                borderRadius: 2,
                                border: '1px solid',
                                borderColor: 'divider',
                                backgroundColor: 'background.paper',
                                overflow: 'hidden',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                p: 2
                            }}
                        >
                            <CardMedia
                                component="img"
                                src={imageSrc}
                                alt={productName}
                                onError={(event) => {
                                    event.currentTarget.src = fallbackImage;
                                }}
                                sx={{ width: '100%', height: '100%', objectFit: 'contain' }}
                            />
                        </Box>
                    </Box>

                    <Box
                        sx={{
                            width: '100%',
                            maxWidth: 720,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            textAlign: 'center',
                            gap: 1
                        }}
                    >
                        <Typography variant="h4" gutterBottom sx={{ fontWeight: 600, mb: 0 }}>
                            {productName}
                        </Typography>
                        <Typography variant="subtitle2" color="text.secondary">
                            {website}
                        </Typography>
                        <Typography variant="h5" color="primary.main" sx={{ fontWeight: 700 }}>
                            {typeof priceValue === 'number' ? priceValue.toLocaleString('en-US') : 'N/A'} ден
                        </Typography>
                    </Box>

                    <Divider sx={{ width: '100%', maxWidth: 720 }} />

                    <Box
                        sx={{
                            width: '100%',
                            maxWidth: 720,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            gap: 2
                        }}
                    >
                        <Typography variant="h6" sx={{ textAlign: 'center' }}>
                            Спецификации
                        </Typography>

                        <TableContainer
                            sx={{
                                width: '100%',
                                border: '1px solid',
                                borderColor: 'divider',
                                borderRadius: 2
                            }}
                        >
                            <Table size="small" aria-label="specifications">
                                <TableBody>
                                    {specifications.length > 0 ? (
                                        specifications.map((spec) => (
                                            <TableRow
                                                key={spec.id}
                                                sx={{ '&:nth-of-type(odd)': { backgroundColor: 'action.hover' } }}
                                            >
                                                <TableCell>{spec.specificationText}</TableCell>
                                            </TableRow>
                                        ))
                                    ) : (
                                        <TableRow>
                                            <TableCell align="center">Нема спецификации.</TableCell>
                                        </TableRow>
                                    )}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                </CardContent>
            </Card>

            {/* Similar Products Button and Feedback */}
            <Box sx={{ textAlign: 'center', mt: 3 }}>
                <Button
                    variant="contained"
                    size="large"
                    onClick={fetchSimilar}
                    sx={{ px: 4, py: 2 }}
                    disabled={isLoadingSimilar} // Disable button while loading
                >
                    {isLoadingSimilar ? <CircularProgress size={24} color="inherit" /> : "Прикажи продукти со слични карактеристики"}
                </Button>
            </Box>

            {/* Display Similar Products or Messages */}
            <Box sx={{ mt: 4 }}>
                {isLoadingSimilar ? (
                    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '200px' }}>
                        <CircularProgress />
                        <Typography variant="h6" sx={{ ml: 2 }}>
                            Finding similar products...
                        </Typography>
                    </Box>
                ) : (
                    hasFetchedSimilar && ( // Only show if fetchSimilar has been called
                        similarProducts.length > 0 ? (
                            <>
                                <Typography variant="h5" gutterBottom sx={{ textAlign: 'center', mb: 3 }}>
                                    Слични продукти
                                </Typography>
                                <ProductsGrid products={similarProducts} />
                            </>
                        ) : (
                            <Typography variant="h6" sx={{ textAlign: 'center', color: 'text.secondary' }}>
                                Нема продукти со слични карактеристики 😔
                            </Typography>
                        )
                    )
                )}
            </Box>
        </Box>
    );
};

export default ProductDetails;
