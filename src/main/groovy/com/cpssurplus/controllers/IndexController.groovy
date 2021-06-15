package com.cpssurplus.controllers

import com.cpssurplus.domains.Brand
import com.cpssurplus.domains.forms.OrderForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("news", [
                new Brand(image: "img/news1.jpg", header: "Injectors for industrial diesel engines",
                        descr: "Injectors for industrial diesel engines genuine, quality replacement or rebuilt. Together will make your business prosper!"),
                new Brand(image: "img/news2.jpg", header: "JCB Parts",
                        descr: "JCB transmision parts & gear. Complete range u/c components and parts for backhoe units. " +
                                "Gear & final drive parts for JCB earth moving macinery. " +
                                "JCB filters. consignment are shipped from our london stores. Our offers are based on JCB inventory at their main parts stores.")
        ])
        return "index"
    }

    @GetMapping("/services")
    String services(Model model) {
        model.addAttribute("agriMachinery", [
                new Brand(image: "img/case.jpg", header: "Case-IH", descr: "Spare parts for case-ih machinery"),
                new Brand(image: "img/new-holland.png", header: "New Holland", descr: "Spare parts for New Holland machinery"),
                new Brand(image: "img/flexi-coil.jpg", header: "Flexi Coil", descr: "Spare parts for Flexi Coil machinery"),
                new Brand(image: "img/fiatagri.jpg", header: "FIATAGRI", descr: "Spare parts for FIATAGRI machinery"),
                new Brand(image: "img/steiger.jpg", header: "STEIGER", descr: "Spare parts for STEIGER machinery"),
                new Brand(image: "img/steyr.jpg", header: "Steyr", descr: "Spare parts for Steyr machinery"),
                new Brand(image: "img/laverda.jpg", header: "Laverda", descr: "Spare parts for Laverda machinery"),
                new Brand(image: "img/massey-ferguson.jpg", header: "Massey Ferguson", descr: "Spare parts for Massey Ferguson machinery"),
                new Brand(image: "img/john-deere.jpg", header: "John Deere", descr: "Spare parts for John Deere machinery"),
                new Brand(image: "img/claas.gif", header: "CLAAS", descr: "Spare parts for CLAAS machinery")
        ])
        model.addAttribute("dieselEngines", [
                new Brand(image: "img/fpt.png", header: "FPT", descr: "Engines and spare parts by FPT"),
                new Brand(image: "img/iveco.jpg", header: "Iveco", descr: "Engines and spare parts by Iveco"),
                new Brand(image: "img/kmp.png", header: "KMP Brand", descr: "Engines and spare parts by KMP Brand"),
                new Brand(image: "img/cummins.jpg", header: "Cummins", descr: "SEngines and spare parts by Cummins"),
                new Brand(image: "img/cat.jpg", header: "CAT", descr: "Engines and spare parts by CAT"),
                new Brand(image: "img/komatsu.jpg", header: "KOMATSU", descr: "Engines and spare parts by KOMATSU")
        ])
        model.addAttribute("earthMoving", [
                new Brand(image: "img/new-holland-constr.jpg", header: "New Holland Construction", descr: "Spare parts for New Holland Construction machinery"),
                new Brand(image: "img/kobelco.png", header: "KOBELCO", descr: "Spare parts for KOBELCO machinery"),
                new Brand(image: "img/fiat-hitachi.jpg", header: "Fiat-Hitachi", descr: "Spare parts for Fiat-Hitachi machinery"),
                new Brand(image: "img/fiat-kobelco.jpg", header: "Fiat-Kobelco", descr: "Spare parts for Fiat-Kobelco machinery"),
                new Brand(image: "img/ok.png", header: "O&K", descr: "Spare parts for O&K machinery")
        ])
        return "services"
    }

    @GetMapping("/contact")
    String contact(Model model) {
        model.addAttribute('orderForm', new OrderForm())
        return "contact"
    }
}
