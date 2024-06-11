import React from 'react';
import MuiSnackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Snackbar = ({ open, autoHideDuration, message, onClose, severity = 'success' }) => {
    return (
        <MuiSnackbar
            open={open}
            autoHideDuration={autoHideDuration}
            onClose={onClose}
        >
            <MuiAlert onClose={onClose} severity={severity} sx={{ width: '100%' }}>
                {message}
            </MuiAlert>
        </MuiSnackbar>
    );
};

export default Snackbar;
