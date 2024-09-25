# Order Service

## How to run?

```bash
docker compose up -d
```

## How to test?

Normally I write Integration Test with Test Container, but testing with Postman should be easier and take less time to
write I believe.
Therefore:

- Import postman collection in postman/order-service.postman_collection.json
- Import postman environment in postman/order-service.postman_environment.json
- Run the test in postman. It will pass I believe :D

