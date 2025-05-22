import React from 'react';
import {Link} from "react-router";
import {AppBar, Box, Button, IconButton, Toolbar, Typography} from "@mui/material";
import "./Header.css";
import AuthenticationToggle from "../../auth/AuthenticationToggle.jsx";

const pages = [
    {"path": "/", "name": "home"},
    {"path": "/accommodations", "name": "accommodations"},
    {"path": "/countries", "name": "countries"},
    {"path": "/hosts", "name": "hosts"},
];

const Header = () => {
    return (
        <Box>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{mr: 2}}
                    >

                    </IconButton>
                    <Typography variant="h6" component="div" sx={{mr: 3}}>
                        E-SHOP
                    </Typography>
                    <Box sx={{flexGrow: 1, display: {xs: "none", md: "flex"}}}>
                        {pages.map((page) => (
                            <Link key={page.name} to={page.path}>
                                <Button
                                    sx={{my: 2, color: "white", display: "block", textDecoration: "none"}}
                                >
                                    {page.name}
                                </Button>
                            </Link>
                        ))}
                    </Box>
                    <AuthenticationToggle/>
                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default Header;