import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { UsersService } from "src/app/Services/Users/users.service";
import { getUserPhoto$action, getUserPhotoFailed$action, getUserPhotoSucceeded$action, refreshUser$action, refreshUserFailed$action, refreshUserSucceeded$action, uploadUserPhoto$action, uploadUserPhotoFailed$action, uploadUserPhotoSucceeded$action } from "./user.actions";
import { catchError, exhaustMap, map } from 'rxjs/operators';
import { of } from "rxjs";
import { ContentService } from "src/app/Services/Content/content.service";

@Injectable()
export class UserEffects {

    constructor(private actions$ : Actions, private service : UsersService, private contentService : ContentService) {}

    refreshUser$ = createEffect(() =>
        this.actions$.pipe(
            ofType(refreshUser$action),
            exhaustMap(() => this.service.current().pipe(
                map((user : any) => refreshUserSucceeded$action({user})),
                catchError(() => of(refreshUserFailed$action()))
            ))
        )
    );

    getUserPhoto$ = createEffect(() =>
        this.actions$.pipe(
            ofType(getUserPhoto$action),
            exhaustMap((payload) => this.contentService.getUserPhoto(payload.userId).pipe(
                map((photo : any) => getUserPhotoSucceeded$action({photo})),
                catchError(() => of(getUserPhotoFailed$action()))
            ))
        )
    );

    uploadUserPhoto$ = createEffect(() =>
        this.actions$.pipe(
            ofType(uploadUserPhoto$action),
            exhaustMap((payload) => this.contentService.uploadUserPhoto(payload.userId, payload.body).pipe(
                map((photo : any) => uploadUserPhotoSucceeded$action({photo})),
                catchError(() => of(uploadUserPhotoFailed$action()))
            ))
        )
    );

}