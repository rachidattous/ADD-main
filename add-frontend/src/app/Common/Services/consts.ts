// application consts and enums

export enum HttpMethod {
  GET = "GET",
  POST = "POST",
  PUT = "PUT",
  DELETE = "DELETE",
  PATCH = "PATCH",
  HEAD = "HEAD",
  CONNECT = "CONNECT",
  OPTIONS = "OPTIONS",
  TRACE = "TRACE"
}

export enum HttpSuccess {
    OK              = 200,
    CREATED         = 201,
    ACCEPTED        = 202,
    NO_CONTENT      = 204,
    RESET_CONTENT   = 205,
    PARTIAL_CONTENT = 206,
    MULTI_STATUS    = 207
    // to elaborate more
}

export enum HttpClientError {
    BAD_REQUEST                     = 400,
    UNAUTHORIZED                    = 401,
    FORBIDDEN                       = 403, 
    NOT_FOUND                       = 404,
    METHOD_NOT_ALLOWED              = 405,
    NOT_ACCEPTABLE                  = 406,
    PROXY_AUTHENTICATION_REQUIRED   = 407,
    REQUEST_TIMEOUT                 = 408,
    NO_RESPONSE_NGINX               = 448,
    CLIENT_CLOSED_REQUEST           = 449
    // to elaborate more
}

export enum HttpServerError {
    INTERNAL_SERVER_ERROR       = 500,
    NOT_IMPLEMENTED             = 501,
    BAD_GATEWAY                 = 502, 
    SERVICE_UNAVAILABLE         = 503,
    GATEWAY_TIMEOUT             = 504,
    HTTP_VERSION_NOT_SUPPORTED  = 505
    // to elaborate more
}

export enum Accept {
    PDF = 'application/pdf',
    EXCEL_97_2003 = 'application/vnd.ms-excel',
    EXCEL = ''
}

export enum Extension {
    PDF = 'pdf',
    EXCEL_97_2003 = 'xls',
    EXCEL = 'xlsx'
}

export enum AuthenticationEndPoints {
    Login = 'api/auth/login',
    Refresh = 'api/auth/refresh',
    Logout = 'api/auth/logout',
}

export enum UsersEndPoints {
    Base = 'api/auth/users',
    Current = 'api/auth/users/current',
    Search = 'api/auth/users/search',
    Delete = 'api/auth/users',
    Export = 'api/auth/users/export/xls',
    Add = 'api/auth/users',
    Update = 'api/auth/users',
    Import = 'api/auth/users/import/xls'
}

export enum PermissionsEndPoints {
    Base = 'api/auth/permissions', 
    All = 'api/auth/permissions/list',
    Export = 'api/auth/permissions/export/xls',
    Import = 'api/auth/permissions/import/xls'
}

export enum RolesEndPoints {
    Base = 'api/auth/roles',
    Export = 'api/auth/roles/export/xls',
    All = 'api/auth/roles/list',
    Import = 'api/auth/roles/import/xls'
}


// to be continued