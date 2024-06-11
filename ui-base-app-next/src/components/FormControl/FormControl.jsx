import React from 'react';
import FormControlMui from "@mui/material/FormControl";

const FormControl = ({children}) => {
    return (
        <FormControlMui sx={{width: 120}}>{children}</FormControlMui>
    );
};

export default FormControl;
