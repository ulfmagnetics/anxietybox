import {
  SecretsManagerClient,
  GetSecretValueCommand,
} from "@aws-sdk/client-secrets-manager";

const secret_name = "prod/anxietybox/apiKey";

const client = new SecretsManagerClient({
  region: "us-east-1",
});

export const handler = async(event) => {
    let response = {
        "isAuthorized": false,
        "context": {},
    };
    let smResponse = {};

    try {
      smResponse = await client.send(
        new GetSecretValueCommand({
          SecretId: secret_name,
          VersionStage: "AWSCURRENT", // VersionStage defaults to AWSCURRENT if unspecified
        })
      );
    } catch (error) {
      // For a list of exceptions thrown, see
      // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
      throw error;
    }

    const secret = smResponse.SecretString;

    if (event.headers.authorization === secret) {
        response = {
            "isAuthorized": true,
            "context": {},
        };
    }

    return response;
};