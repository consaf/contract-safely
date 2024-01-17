declare module NodeJS {
    interface ProcessEnv {
        NEXT_PUBLIC_API_URL_DEV: string;
        NEXT_PUBLIC_CLIENT_ID: string;
        NEXT_PUBLIC_CALLBACK_URL: string;
    }
}
