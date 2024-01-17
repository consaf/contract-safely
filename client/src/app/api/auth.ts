interface NaverAuthRequest {
    authorizationCode: string;
    state: string;
}

interface NaverAuthResponse {
    accessToken: string;
}

const BASE_URL = process.env.NEXT_PUBLIC_API_URL_DEV;

export const naverLogin = async (body: NaverAuthRequest): Promise<NaverAuthResponse> => {
    const response = await fetch(`${BASE_URL}/oauth/naver`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
        credentials: 'include',
    });

    if (!response.ok) {
        throw new Error('error');
    }

    return await response.json();
};
