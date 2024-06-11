import React from 'react';
import useTheme from 'misc/hooks/useTheme';
import SvgIcon from '../SvgIcon';

const ListAltIcon = ({
                         color = 'default', // default | header | error | success | warning | info | <string>
                         size = 24,
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
            <path
                fill={actualColor}
                d="M4 5h16v2H4zm0 4h16v2H4zm0 4h16v2H4zm0 4h16v2H4z"
            />
        </SvgIcon>
    );
};

export default ListAltIcon;
