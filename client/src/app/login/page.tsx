'use client';

import NaverButton from '@/components/common/icon/NaverButton';
import { STATE } from '@/constants/auth';

export default function Login() {
    const CLIENT_ID = process.env.NEXT_PUBLIC_CLIENT_ID;
    const CALLBACK_URL = process.env.NEXT_PUBLIC_CALLBACK_URL;

    const goNaverLogin = () => {
        window.location.href = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${CLIENT_ID}&redirect_uri=${CALLBACK_URL}&state=${STATE}`;
    };

    return (
        <div>
            <h1>로그인 페이지입니다</h1>

            <button onClick={goNaverLogin}>
                <NaverButton label="login" />
            </button>
        </div>
    );
}
