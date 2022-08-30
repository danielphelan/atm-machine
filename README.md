
# Project Title

A brief description of what this project does and who it's for


## Run Locally

Clone the project

```bash
  git clone https://github.com/danielphelan/atm-machine.git
```

Go to the project directory

```bash
  cd atm-machine
```

Build

```bash
  maven clean install
```

Start the application

```bash
  maven spring-boot:run
```


## API Reference

#### Get ATM Machine Notes/Balance

```http
  GET /api/items
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |

#### Get Account Balance

```http
  GET /api/items/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |


