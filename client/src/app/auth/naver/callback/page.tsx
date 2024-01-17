'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';

import { naverLogin } from '@/api/auth';

import { ACCESS_TOKEN_KEY } from '@/utils/fetch';
import { setLocalStorage } from '@/utils/localStorage';
import { decodeToken } from '@/utils/decodeToken';
import { STATE } from '@/constants/auth';

const API_BASE_URL_DEV = 'http://api.consaf.com';
const API_BASE_URL_PROD = 'http://localhost:8080';

export default function RedirectionPage() {
    //  const { loggedInfo, setLoggedInfo } = useContext(AuthContext);
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoading, setIsLoading] = useState(true);
    const params = useSearchParams();

    const router = useRouter();

    useEffect(() => {
        const authInfoFetch = async () => {
            setIsLoading(true);
            setErrorMessage('');

            const authorizationCode = params.get('code') as string;

            try {
                const { accessToken } = await naverLogin({
                    authorizationCode,
                    state: STATE,
                });
                setLocalStorage(ACCESS_TOKEN_KEY, accessToken);

                const decodedPayload = decodeToken(accessToken);
                //  const id = decodedPayload.memberId;

                window.console.log(decodedPayload);

                router.push('/');
            } catch (error) {
                setErrorMessage('로그인 중 오류가 발생했습니다.');
            } finally {
                setIsLoading(false);
            }
        };

        authInfoFetch();
    }, [router, params]);

    if (isLoading) return <div>로딩 중...</div>;

    if (errorMessage) return <div>로그인 중 오류가 발생했습니다.</div>;
}
