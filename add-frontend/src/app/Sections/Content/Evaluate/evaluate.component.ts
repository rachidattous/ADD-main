import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-evaluate',
  templateUrl: './evaluate.component.html',
  styleUrls: ['./evaluate.component.scss']
})
export class EvaluateComponent implements OnInit {

  evaluationForm !: FormGroup

  constructor(private dialogRef : MatDialogRef<EvaluateComponent>, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.evaluationForm = this.fb.group({
      validationType : [null, Validators.required],
      comment : [''],
    })
  }

  evaluate(){
    this.dialogRef.close(this.evaluationForm.value);
  }

}
