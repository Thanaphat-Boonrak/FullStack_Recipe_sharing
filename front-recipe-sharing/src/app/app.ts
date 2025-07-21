import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './pages/navbar/navbar';
import { Footer } from './pages/footer/footer';
@Component({
  selector: 'app-root',
  imports: [Footer, RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected title = 'front-recipe-sharing';
}
