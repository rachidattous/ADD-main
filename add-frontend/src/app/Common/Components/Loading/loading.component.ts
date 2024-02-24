import { Component} from '@angular/core';
import { Subject } from 'rxjs';
import { LoadingService } from '../../Services/UI/Loading/loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {

  isLoading: Subject<boolean> = this.loadingService.isLoading;

  constructor(private loadingService : LoadingService) { }

}
