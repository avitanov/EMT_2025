import React from 'react';
import {Link} from "react-router";
import {AppBar, Box, Button, Toolbar, Typography} from "@mui/material";
import "./Header.css";

const pages = [
    {"path": "/products", "name": "Дома"},
    {"path": "/products/frizideri", "name": "Фрижидери"},
    {"path": "/products/inverteri", "name": "Инвертери"},
];

const Header = () => {
        return (
            <Box>
            <AppBar position="static" sx={{backgroundColor: "#0f172a"}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{mr: 3, fontWeight: 700, letterSpacing: "0.08em"}}>
                        E-SHOP
                    </Typography>
                    <Box
                        sx={{
                            flexGrow: 1,
                            display: "flex",
                            flexWrap: "wrap",
                            justifyContent: {xs: "flex-end", md: "flex-start"},
                            gap: 1
                        }}
                    >
                        {pages.map((page) => (
                            <Link key={page.name} to={page.path}>
                                <Button
                                    sx={{
                                        my: 1,
                                        px: 2,
                                        color: "white",
                                        display: "block",
                                        textDecoration: "none",
                                        borderRadius: "999px",
                                        backgroundColor: "rgba(255,255,255,0.08)",
                                        "&:hover": {
                                            backgroundColor: "rgba(255,255,255,0.16)"
                                        }
                                    }}
                                >
                                    {page.name}
                                </Button>
                            </Link>
                        ))}
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default Header;
