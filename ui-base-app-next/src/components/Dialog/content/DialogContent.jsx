import React from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';

const CustomDialogContent = ({onClose, onConfirm, message, primaryNameBtn, secondaryNameBtn, type, title}) => {
    return (
        <>
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {message}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="primary">
                    {primaryNameBtn}
                </Button>
                {type === "basic" &&
                    <Button onClick={onConfirm} color="secondary">
                        {secondaryNameBtn}
                    </Button>}
            </DialogActions>
        </>
    );
};

export default CustomDialogContent;
