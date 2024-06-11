import React from 'react';
import SvgIcon from '@mui/material/SvgIcon';
import { useTheme } from '@mui/material/styles';

const Edit = ({ color = 'default', size = 24 }) => {
    const { palette } = useTheme();
    const actualColor = palette[color] || color;

    return (
        <SvgIcon style={{ fontSize: size }} viewBox="0 0 24 24">
            <path
                fill={actualColor}
                d="M20.71 7.04l-3.75-3.75a.996.996 0 00-1.41 0L3.29 16.29a.996.996 0 00-.29.7V21a1 1 0 001 1h4.01c.26 0 .51-.1.71-.29l12.01-12a.996.996 0 00.29-.71l-.03-.03a.975.975 0 00-.29-.68zM7 20V16.41L16.59 6.83l2.58 2.58L9.41 20H7z"
            />
        </SvgIcon>
    );
};

export default Edit;
