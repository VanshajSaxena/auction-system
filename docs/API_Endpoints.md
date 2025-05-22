# Online Auction System API Endpoints

This section provides detailed documentation for each API endpoint available in the Online Auction System, including request and response bodies, and authorization requirements.

## API Endpoints

The base URL for all API endpoints is `http://127.0.0.1:8080/api/v1`.

### Authentication and Authorization

#### Authenticate User

**POST /auth/login**

Authenticates a user and returns authentication tokens.

| Parameter  | Type                                                   | Description           |
| ---------- | ------------------------------------------------------ | --------------------- |
| `email`    | `string` (email format)                                | User's email address. |
| `username` | `string` (pattern: `^[A-Za-z0-9_-]+$`, max length: 16) | User's username.      |
| `password` | `string` (password format, min length: 8)              | User's password.      |

**Request Body**

The request must include a `password` and exactly one of either `email` or `username`.

| Field      | Type                | Description                             | Example             |
| ---------- | ------------------- | --------------------------------------- | ------------------- |
| `email`    | `string` (email)    | The user's email address.               | `user@example.com`  |
| `username` | `string`            | The user's username.                    | `johndoe`           |
| `password` | `string` (password) | The user's password (min 8 characters). | `securepassword123` |

**Response Body (200 OK)**

| Field          | Type              | Description                                     | Example                 |
| -------------- | ----------------- | ----------------------------------------------- | ----------------------- |
| `refreshToken` | `string`          | Token for refreshing access token.              | `eyJhbGciOiJIUzI1Ni...` |
| `accessToken`  | `string`          | Access token for authenticated requests.        | `eyJhbGciOiJIUzI1Ni...` |
| `expiresIn`    | `integer` (int32) | Expiration time of the access token in seconds. | `3600`                  |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Register New User

**POST /auth/register**

Registers a new user in the system.

**Request Body**

| Field       | Type                                                   | Description                     | Example                |
| ----------- | ------------------------------------------------------ | ------------------------------- | ---------------------- |
| `firstName` | `string`                                               | The user's first name.          | `John`                 |
| `lastName`  | `string`                                               | The user's last name.           | `Doe`                  |
| `username`  | `string` (pattern: `^[A-Za-z0-9_-]+$`, max length: 16) | A unique username for the user. | `johndoe`              |
| `email`     | `string` (email)                                       | The user's email address.       | `john.doe@example.com` |
| `password`  | `string` (password, min length: 8)                     | The user's password.            | `myStrongP@ssw0rd`     |

**Response Body (201 Created)**

| Field     | Type              | Description                                          | Example                         |
| --------- | ----------------- | ---------------------------------------------------- | ------------------------------- |
| `success` | `boolean`         | Indicates if the registration was successful.        | `true`                          |
| `message` | `string`          | A message indicating the result of the registration. | `User registered successfully.` |
| `userId`  | `integer` (int64) | The ID of the newly registered user.                 | `12345`                         |

**Error Response (default)**

See [Default Error Response](#default-error-response).

### Users

#### Get All Users

**GET /users**

Retrieves a list of all registered users in the system.

**Responses (200 OK)**

An array of `User` objects.

| Field           | Type                                           | Description                                 | Example                  |
| --------------- | ---------------------------------------------- | ------------------------------------------- | ------------------------ |
| `id`            | `integer` (int64)                              | Unique identifier for the user. (Read-only) | `1`                      |
| `firstName`     | `string`                                       | User's first name.                          | `Jane`                   |
| `lastName`      | `string`                                       | User's last name.                           | `Smith`                  |
| `username`      | `string`                                       | User's username.                            | `janesmith`              |
| `email`         | `string` (email)                               | User's email address.                       | `jane.smith@example.com` |
| `contactNumber` | `string`                                       | User's contact number.                      | `+1234567890`            |
| `shippingAddr`  | `object` (`UserAddress`)                       | User's shipping address.                    | See `UserAddress` schema |
| `roles`         | `array` of `string` (enum: `Seller`, `Bidder`) | Roles assigned to the user.                 | `["Bidder", "Seller"]`   |

#### Get Current User Profile

**GET /users/me**

Retrieves the profile information of the currently logged-in user.

**Authorization:** Requires `bearerAuth` (Access Token).

**Response Body (200 OK)**

A `User` object representing the logged-in user.

| Field           | Type                                           | Description                                 | Example                  |
| --------------- | ---------------------------------------------- | ------------------------------------------- | ------------------------ |
| `id`            | `integer` (int64)                              | Unique identifier for the user. (Read-only) | `1`                      |
| `firstName`     | `string`                                       | User's first name.                          | `Jane`                   |
| `lastName`      | `string`                                       | User's last name.                           | `Smith`                  |
| `username`      | `string`                                       | User's username.                            | `janesmith`              |
| `email`         | `string` (email)                               | User's email address.                       | `jane.smith@example.com` |
| `contactNumber` | `string`                                       | User's contact number.                      | `+1234567890`            |
| `shippingAddr`  | `object` (`UserAddress`)                       | User's shipping address.                    | See `UserAddress` schema |
| `roles`         | `array` of `string` (enum: `Seller`, `Bidder`) | Roles assigned to the user.                 | `["Bidder", "Seller"]`   |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Get Current User's Past Bids

**GET /users/me/bids**

Retrieves a list of all bids made by the currently logged-in user.

**Authorization:** Requires `bearerAuth` (Access Token).

**Response Body (200 OK)**

An array of `Bid` objects.

| Field         | Type                                             | Description                                | Example                |
| ------------- | ------------------------------------------------ | ------------------------------------------ | ---------------------- |
| `id`          | `integer` (int64)                                | Unique identifier for the bid. (Read-only) | `101`                  |
| `userId`      | `integer` (int64)                                | The ID of the user who placed the bid.     | `1`                    |
| `bidAmount`   | `number` (double)                                | The amount of the bid.                     | `150.75`               |
| `bidTime`     | `string` (date-time)                             | The timestamp when the bid was placed.     | `2024-05-20T10:30:00Z` |
| `bidValidity` | `string` (enum: `valid`, `invalid`, `retracted`) | The current validity status of the bid.    | `valid`                |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Get Current User's Auction Listings

**GET /users/me/listings**

Retrieves a list of all auction listings created by the currently logged-in user.

**Authorization:** Requires `bearerAuth` (Access Token).

**Response Body (200 OK)**

An array of `AuctionListing` objects.

| Field                 | Type                                                                     | Description                                             | Example                           |
| --------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------- | --------------------------------- |
| `id`                  | `integer` (int64)                                                        | Unique identifier for the auction listing. (Read-only)  | `201`                             |
| `auctionListingState` | `string` (enum: `pending`, `active`, `closed`, `cancelled`, `completed`) | The current state of the auction listing. (Read-only)   | `active`                          |
| `creatorId`           | `integer` (int64)                                                        | The ID of the user who created the listing. (Read-only) | `1`                               |
| `description`         | `string`                                                                 | A description of the auction item.                      | `Vintage collectible comic book.` |
| `startingPrice`       | `number` (double)                                                        | The initial price of the auction.                       | `100.00`                          |
| `reservePrice`        | `number` (double)                                                        | The minimum price at which the item will be sold.       | `150.00`                          |
| `startTime`           | `string` (date-time)                                                     | The start time of the auction.                          | `2024-05-20T09:00:00Z`            |
| `endTime`             | `string` (date-time)                                                     | The end time of the auction.                            | `2024-05-27T09:00:00Z`            |
| `auctionItem`         | `object` (`AuctionItem`)                                                 | Details of the item being auctioned.                    | See `AuctionItem` schema          |

**Error Response (default)**

See [Default Error Response](#default-error-response).

### Auction Listings

#### Create Auction Listing

**POST /auctions**

Creates a new auction listing.

**Request Body**

A `AuctionListing` object (excluding read-only fields `id`, `auctionListingState`, `creatorId`).

| Field           | Type                     | Description                                       | Example                  |
| --------------- | ------------------------ | ------------------------------------------------- | ------------------------ |
| `description`   | `string`                 | A description of the auction item.                | `Rare antique vase.`     |
| `startingPrice` | `number` (double)        | The initial price of the auction.                 | `500.00`                 |
| `reservePrice`  | `number` (double)        | The minimum price at which the item will be sold. | `750.00`                 |
| `startTime`     | `string` (date-time)     | The start time of the auction.                    | `2024-05-25T14:00:00Z`   |
| `endTime`       | `string` (date-time)     | The end time of the auction.                      | `2024-06-01T14:00:00Z`   |
| `auctionItem`   | `object` (`AuctionItem`) | Details of the item being auctioned.              | See `AuctionItem` schema |

**Response Body (201 Created)**

A `AuctionListing` object representing the newly created auction.

| Field                 | Type                                                                     | Description                                             | Example                  |
| --------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------- | ------------------------ |
| `id`                  | `integer` (int64)                                                        | Unique identifier for the auction listing. (Read-only)  | `202`                    |
| `auctionListingState` | `string` (enum: `pending`, `active`, `closed`, `cancelled`, `completed`) | The current state of the auction listing. (Read-only)   | `pending`                |
| `creatorId`           | `integer` (int64)                                                        | The ID of the user who created the listing. (Read-only) | `1`                      |
| `description`         | `string`                                                                 | A description of the auction item.                      | `Rare antique vase.`     |
| `startingPrice`       | `number` (double)                                                        | The initial price of the auction.                       | `500.00`                 |
| `reservePrice`        | `number` (double)                                                        | The minimum price at which the item will be sold.       | `750.00`                 |
| `startTime`           | `string` (date-time)                                                     | The start time of the auction.                          | `2024-05-25T14:00:00Z`   |
| `endTime`             | `string` (date-time)                                                     | The end time of the auction.                            | `2024-06-01T14:00:00Z`   |
| `auctionItem`         | `object` (`AuctionItem`)                                                 | Details of the item being auctioned.                    | See `AuctionItem` schema |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Get All Auction Listings

**GET /auctions**

Retrieves a list of all active auction listings.

**Response Body (200 OK)**

An array of `AuctionListing` objects.

| Field                 | Type                                                                     | Description                                             | Example                           |
| --------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------- | --------------------------------- |
| `id`                  | `integer` (int64)                                                        | Unique identifier for the auction listing. (Read-only)  | `201`                             |
| `auctionListingState` | `string` (enum: `pending`, `active`, `closed`, `cancelled`, `completed`) | The current state of the auction listing. (Read-only)   | `active`                          |
| `creatorId`           | `integer` (int64)                                                        | The ID of the user who created the listing. (Read-only) | `1`                               |
| `description`         | `string`                                                                 | A description of the auction item.                      | `Vintage collectible comic book.` |
| `startingPrice`       | `number` (double)                                                        | The initial price of the auction.                       | `100.00`                          |
| `reservePrice`        | `number` (double)                                                        | The minimum price at which the item will be sold.       | `150.00`                          |
| `startTime`           | `string` (date-time)                                                     | The start time of the auction.                          | `2024-05-20T09:00:00Z`            |
| `endTime`             | `string` (date-time)                                                     | The end time of the auction.                            | `2024-05-27T09:00:00Z`            |
| `auctionItem`         | `object` (`AuctionItem`)                                                 | Details of the item being auctioned.                    | See `AuctionItem` schema          |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Update Auction Listing

**PUT /auctions/{auctionId}**

Updates an existing auction listing. Only the creator of the auction is allowed to update.

**Path Parameters**

| Name        | Type              | Description                              |
| ----------- | ----------------- | ---------------------------------------- |
| `auctionId` | `integer` (int64) | The ID of the auction listing to update. |

**Authorization:** Requires `bearerAuth` (Access Token).

**Request Body**

An `AuctionListing` object (excluding read-only fields `id`, `auctionListingState`, `creatorId`).

| Field           | Type                     | Description                                       | Example                                   |
| --------------- | ------------------------ | ------------------------------------------------- | ----------------------------------------- |
| `description`   | `string`                 | An updated description of the auction item.       | `Rare antique vase, excellent condition.` |
| `startingPrice` | `number` (double)        | The initial price of the auction.                 | `550.00`                                  |
| `reservePrice`  | `number` (double)        | The minimum price at which the item will be sold. | `800.00`                                  |
| `startTime`     | `string` (date-time)     | The start time of the auction.                    | `2024-05-25T14:00:00Z`                    |
| `endTime`       | `string` (date-time)     | The end time of the auction.                      | `2024-06-05T14:00:00Z`                    |
| `auctionItem`   | `object` (`AuctionItem`) | Updated details of the item being auctioned.      | See `AuctionItem` schema                  |

**Response Body (200 OK)**

A `AuctionListing` object representing the updated auction.

| Field                 | Type                                                                     | Description                                             | Example                                   |
| --------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------- | ----------------------------------------- |
| `id`                  | `integer` (int64)                                                        | Unique identifier for the auction listing. (Read-only)  | `202`                                     |
| `auctionListingState` | `string` (enum: `pending`, `active`, `closed`, `cancelled`, `completed`) | The current state of the auction listing. (Read-only)   | `pending`                                 |
| `creatorId`           | `integer` (int64)                                                        | The ID of the user who created the listing. (Read-only) | `1`                                       |
| `description`         | `string`                                                                 | An updated description of the auction item.             | `Rare antique vase, excellent condition.` |
| `startingPrice`       | `number` (double)                                                        | The initial price of the auction.                       | `550.00`                                  |
| `reservePrice`        | `number` (double)                                                        | The minimum price at which the item will be sold.       | `800.00`                                  |
| `startTime`           | `string` (date-time)                                                     | The start time of the auction.                          | `2024-05-25T14:00:00Z`                    |
| `endTime`             | `string` (date-time)                                                     | The end time of the auction.                            | `2024-06-05T14:00:00Z`                    |
| `auctionItem`         | `object` (`AuctionItem`)                                                 | Updated details of the item being auctioned.            | See `AuctionItem` schema                  |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Get Specific Auction Listing

**GET /auctions/{auctionId}**

Retrieves details of a specific auction listing by its ID.

**Path Parameters**

| Name        | Type              | Description                                |
| ----------- | ----------------- | ------------------------------------------ |
| `auctionId` | `integer` (int64) | The ID of the auction listing to retrieve. |

**Response Body (200 OK)**

An `AuctionListing` object.

| Field                 | Type                                                                     | Description                                             | Example                           |
| --------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------- | --------------------------------- |
| `id`                  | `integer` (int64)                                                        | Unique identifier for the auction listing. (Read-only)  | `201`                             |
| `auctionListingState` | `string` (enum: `pending`, `active`, `closed`, `cancelled`, `completed`) | The current state of the auction listing. (Read-only)   | `active`                          |
| `creatorId`           | `integer` (int64)                                                        | The ID of the user who created the listing. (Read-only) | `1`                               |
| `description`         | `string`                                                                 | A description of the auction item.                      | `Vintage collectible comic book.` |
| `startingPrice`       | `number` (double)                                                        | The initial price of the auction.                       | `100.00`                          |
| `reservePrice`        | `number` (double)                                                        | The minimum price at which the item will be sold.       | `150.00`                          |
| `startTime`           | `string` (date-time)                                                     | The start time of the auction.                          | `2024-05-20T09:00:00Z`            |
| `endTime`             | `string` (date-time)                                                     | The end time of the auction.                            | `2024-05-27T09:00:00Z`            |
| `auctionItem`         | `object` (`AuctionItem`)                                                 | Details of the item being auctioned.                    | See `AuctionItem` schema          |

**Error Response (default)**

See [Default Error Response](#default-error-response).

### Bids

#### Place Bid on Auction

**POST /auctions/{auctionId}/bids**

Places a bid on a specific auction listing.

**Path Parameters**

| Name        | Type              | Description                                      |
| ----------- | ----------------- | ------------------------------------------------ |
| `auctionId` | `integer` (int64) | The ID of the auction listing to place a bid on. |

**Request Body**

A `Bid` object (excluding read-only fields `id`, `userId`, `bidTime`, `bidValidity`).

| Field       | Type              | Description            | Example  |
| ----------- | ----------------- | ---------------------- | -------- |
| `bidAmount` | `number` (double) | The amount of the bid. | `175.50` |

**Response Body (201 Created)**

A `Bid` object representing the newly placed bid.

| Field         | Type                                             | Description                                | Example                |
| ------------- | ------------------------------------------------ | ------------------------------------------ | ---------------------- |
| `id`          | `integer` (int64)                                | Unique identifier for the bid. (Read-only) | `102`                  |
| `userId`      | `integer` (int64)                                | The ID of the user who placed the bid.     | `2`                    |
| `bidAmount`   | `number` (double)                                | The amount of the bid.                     | `175.50`               |
| `bidTime`     | `string` (date-time)                             | The timestamp when the bid was placed.     | `2024-05-22T15:45:00Z` |
| `bidValidity` | `string` (enum: `valid`, `invalid`, `retracted`) | The current validity status of the bid.    | `valid`                |

**Error Response (default)**

See [Default Error Response](#default-error-response).

#### Get All Bids of Auction

**GET /auctions/{auctionId}/bids**

Retrieves the bidding history for a specific auction listing.

**Path Parameters**

| Name        | Type              | Description                                         |
| ----------- | ----------------- | --------------------------------------------------- |
| `auctionId` | `integer` (int64) | The ID of the auction listing to retrieve bids for. |

**Response Body (200 OK)**

An array of `Bid` objects.

| Field         | Type                                             | Description                                | Example                |
| ------------- | ------------------------------------------------ | ------------------------------------------ | ---------------------- |
| `id`          | `integer` (int64)                                | Unique identifier for the bid. (Read-only) | `101`                  |
| `userId`      | `integer` (int64)                                | The ID of the user who placed the bid.     | `1`                    |
| `bidAmount`   | `number` (double)                                | The amount of the bid.                     | `150.75`               |
| `bidTime`     | `string` (date-time)                             | The timestamp when the bid was placed.     | `2024-05-20T10:30:00Z` |
| `bidValidity` | `string` (enum: `valid`, `invalid`, `retracted`) | The current validity status of the bid.    | `valid`                |
| `id`          | `integer` (int64)                                | Unique identifier for the bid. (Read-only) | `102`                  |
| `userId`      | `integer` (int64)                                | The ID of the user who placed the bid.     | `2`                    |
| `bidAmount`   | `number` (double)                                | The amount of the bid.                     | `175.50`               |
| `bidTime`     | `string` (date-time)                             | The timestamp when the bid was placed.     | `2024-05-22T15:45:00Z` |
| `bidValidity` | `string` (enum: `valid`, `invalid`, `retracted`) | The current validity status of the bid.    | `valid`                |

**Error Response (default)**

See [Default Error Response](#default-error-response).

### Schemas

#### UserAddress

User's address.

| Field  | Type     | Description                            | Example                 |
| ------ | -------- | -------------------------------------- | ----------------------- |
| `row1` | `string` | First line of the address.             | `123 Main St`           |
| `row2` | `string` | Second line of the address (optional). | `Apt 4B`                |
| `row3` | `string` | Third line of the address (optional).  | `Springfield, IL 62701` |

#### AuctionItem

Item listed for a specific Auction Listing.

| Field                  | Type                                               | Description                                         | Example                                                    |
| ---------------------- | -------------------------------------------------- | --------------------------------------------------- | ---------------------------------------------------------- |
| `id`                   | `integer` (int64)                                  | Unique identifier for the auction item. (Read-only) | `301`                                                      |
| `ownerId`              | `integer` (int64)                                  | The ID of the user who owns the item. (Read-only)   | `1`                                                        |
| `name`                 | `string`                                           | The name of the item.                               | `Rare Stamp Collection`                                    |
| `category`             | `string`                                           | The category of the item.                           | `Collectibles`                                             |
| `description`          | `string`                                           | A detailed description of the item.                 | `A rare collection of stamps from the early 20th century.` |
| `auctionItemCondition` | `string` (enum: `new_item`, `used`, `refurbished`) | The condition of the item.                          | `used`                                                     |

### Default Error Response

**DefaultError**

Central error response object for unexpected errors.

| Field              | Type               | Description                                  | Example                                                   |
| ------------------ | ------------------ | -------------------------------------------- | --------------------------------------------------------- |
| `status`           | `integer` (int32)  | The HTTP status code of the error.           | `400`                                                     |
| `message`          | `string`           | A general error message.                     | `Bad Request`                                             |
| `fields`           | `array` of objects | A list of specific field-related errors.     | `[{"field": "email", "message": "Invalid email format"}]` |
| `fields[].field`   | `string`           | The name of the field that caused the error. | `email`                                                   |
| `fields[].message` | `string`           | A specific error message for the field.      | `Invalid email format`                                    |
