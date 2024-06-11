import React from 'react';
import useTheme from '../../misc/hooks/useTheme';
import SvgIcon from '../SvgIcon';

const DeleteIcon = ({
                        color = 'default', // default | header | error | success | warning | info | <string>
                        size = 32,
                    }) => {
    const { theme } = useTheme();
    const actualColor = theme.icon.color[color] || color;

    return (
        <SvgIcon
            style={{
                height: `${size}px`,
                width: `${size}px`,
            }}
            viewBox="0 0 24 24"
        >
            <g transform="translate(-709.000000, -65.000000)">
                <g transform="translate(709.000000, 65.000000)">
                    <path
                        fill={actualColor}
                        d="M16 9v10H8V9h8m-1.5-6h-5l-1 1H5v2h14V4h-3.5l-1-1zm2.5 3H6v12c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V6z"
                    />
                </g>
            </g>
        </SvgIcon>
    );
};

export default DeleteIcon;
