import { useState } from 'react';

const useMockManager = (initialValue = false) => {
    const [mockManager, setMockManager] = useState(initialValue);

    return [mockManager, setMockManager];
};

export default useMockManager;
