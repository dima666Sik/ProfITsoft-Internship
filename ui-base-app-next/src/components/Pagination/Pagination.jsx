import React from 'react';
import PaginationMui from '@mui/material/Pagination';
import {Box} from "@mui/material";
import useTheme from "../../misc/hooks/useTheme";

const Pagination = ({count, variant, page, onChange}) => {
    return (
        <Box display="flex" justifyContent="center" mt={2}>
            <PaginationMui count={count} variant={variant} page={page} onChange={onChange}/>
        </Box>
    );
};

export default Pagination;
