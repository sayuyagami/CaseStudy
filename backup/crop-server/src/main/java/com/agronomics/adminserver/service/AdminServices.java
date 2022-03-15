package com.agronomics.adminserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agronomics.adminserver.models.Crops;
import com.agronomics.adminserver.models.KharifCrops;
import com.agronomics.adminserver.models.ListCrops;
import com.agronomics.adminserver.models.Purchases;
import com.agronomics.adminserver.models.RabbiCrops;
import com.agronomics.adminserver.repositories.KharifRepository;
import com.agronomics.adminserver.repositories.RabbiRepository;

@Service
public class AdminServices {

	@Autowired
	KharifRepository kharifrepo;
	
	@Autowired
	RabbiRepository rabbirepo;
	
	//get crops details
	public ListCrops Allcrops() throws Exception {
		
		ListCrops lscrps = new ListCrops();
		if(kharifrepo.findAll()==null) { throw new Exception("No data");}
		lscrps.setKharif(kharifrepo.findAll());
		
		if(rabbirepo.findAll()==null) { throw new Exception("No data");}
		lscrps.setRabbi(rabbirepo.findAll());
		return lscrps;
	}
	
	//get crops details
	public ListCrops Allcropsbyfarmerid(long farmerid) throws Exception {

		ListCrops lscrps = new ListCrops();
		if(kharifrepo.findAll()==null) { throw new Exception("No data");}
		List<KharifCrops> lskc=kharifrepo.findAll().stream().filter((farmer)->farmer.getFarmerid().equals(farmerid)).toList();

		if(rabbirepo.findAll()==null) { throw new Exception("No data");}
		List<RabbiCrops> lsrc=rabbirepo.findAll().stream().filter((farmer)->farmer.getFarmerid().equals(farmerid)).toList();
		
		lscrps.setKharif(lskc);
		lscrps.setRabbi(lsrc);
		return lscrps;
	}
	
	//get Kharifcrops details
	public List<KharifCrops> AllKharifcrops() throws Exception {
		if(kharifrepo.findAll()==null) { throw new Exception("No data");}
		else
			return kharifrepo.findAll();
	}

	public List<RabbiCrops> AllRabbicrops() throws Exception {
		// TODO Auto-generated method stub
		if(rabbirepo.findAll()==null) { throw new Exception("No data");}
		else
			return rabbirepo.findAll();
	}

	public void Receivespostsfromfarmer(Crops message) {
		if(message.getCroptype().equals("KharifCrops")) {
			KharifCrops kc= new KharifCrops();
			Long num= 0L;
			if(kharifrepo.findAll()!=null) {
				num=(long) kharifrepo.findAll().size();
			}
			kc.setCropid(num+1);
			kc.setFarmerid(message.getFarmerid());
			kc.setCroplocation(message.getCroplocation());
			kc.setCropname(message.getCropname());
			kc.setCropprice(message.getCropprice());
			kc.setCropqty(message.getCropqty());
			kc.setCroptype(message.getCroptype());
			kc.setFarmername(message.getFarmername());
			kc.setCropstatus("Available");
			kharifrepo.save(kc);
		}else if(message.getCroptype().equals("RabbiCrops")) {
			RabbiCrops rc= new RabbiCrops();
			Long num= 0L;
			if(rabbirepo.findAll()!=null) {
				num=(long) rabbirepo.findAll().size();
			}
			rc.setCropid(num+1);
			rc.setFarmerid(message.getFarmerid());
			rc.setCroplocation(message.getCroplocation());
			rc.setCropname(message.getCropname());
			rc.setCropprice(message.getCropprice());
			rc.setCropqty(message.getCropqty());
			rc.setCroptype(message.getCroptype());
			rc.setFarmername(message.getFarmername());
			rc.setCropstatus("Available");
			rabbirepo.save(rc);
		}
		
	}

	public Optional<KharifCrops> Kharifcropsbyid(Long cropid) {
		// TODO Auto-generated method stub
		return kharifrepo.findById(cropid);
	}

	public Optional<RabbiCrops> rabbicropsbyid(Long cropid) {
		// TODO Auto-generated method stub
		return rabbirepo.findById(cropid);
	}

	public void updatepostforfarmer(Crops message) {
		// TODO Auto-generated method stub
		if(message.getCroptype().equals("KharifCrops")) {
			KharifCrops kc= kharifrepo.findById(message.getCropid()).get();
			if(message.getFarmerid().equals(kc.getFarmerid())) {
			
				kc.setCropid(kc.getCropid());
				kc.setFarmerid(kc.getFarmerid());
				kc.setCroplocation(message.getCroplocation());
				kc.setCropname(kc.getCropname());
				kc.setCropprice(message.getCropprice());
				kc.setCropqty(message.getCropqty());
				kc.setCroptype(kc.getCroptype());
				kc.setFarmername(kc.getFarmername());
				kharifrepo.save(kc);
			}
			
		}else if(message.getCroptype().equals("RabbiCrops")) {
			RabbiCrops rc= rabbirepo.findById(message.getCropid()).get();
			if(message.getFarmerid().equals(rc.getFarmerid())) {
				rc.setCropid(rc.getCropid());
				rc.setFarmerid(rc.getFarmerid());
				rc.setCroplocation(message.getCroplocation());
				rc.setCropname(rc.getCropname());
				rc.setCropprice(message.getCropprice());
				rc.setCropqty(message.getCropqty());
				rc.setCroptype(rc.getCroptype());
				rc.setFarmername(rc.getFarmername());
				rabbirepo.save(rc);
			}
		}
		
		
	}

	public void requestcropfromdealer(Crops message) {
		// TODO Auto-generated method stub
		if(message.getCroptype().equals("KharifCrops")) {
			KharifCrops kc= kharifrepo.findById(message.getReqcropid()).get();
			Purchases p =new Purchases();
			p.setDealerid(message.getDealerid());
			p.setDealername(message.getDealername());
			p.setNegotiateprice(message.getNegotiateprice());
			p.setPurchaseid(message.getPurchaseid());
			p.setReqstatus(message.getReqstatus());
			List<Purchases> lsreqs = new ArrayList<>();
			if(kc.getRequests()!=null){
				lsreqs=kc.getRequests();
			}
				lsreqs.add(p);
				kc.setRequests(lsreqs);
				kharifrepo.save(kc);	
		}
			
		else if(message.getCroptype().equals("RabbiCrops")) {
			RabbiCrops rc= rabbirepo.findById(message.getReqcropid()).get();
			Purchases p =new Purchases();
			p.setDealerid(message.getDealerid());
			p.setDealername(message.getDealername());
			p.setNegotiateprice(message.getNegotiateprice());
			p.setPurchaseid(message.getPurchaseid());
			p.setReqstatus(message.getReqstatus());
			List<Purchases> lsreqs = new ArrayList<>();
			if(rc.getRequests()!=null){
				lsreqs=rc.getRequests();
			}
				lsreqs.add(p);
				rc.setRequests(lsreqs);
				rabbirepo.save(rc);
		  }
	}

	public void updaterequestcropfromdealer(Crops message) {
		
		if(message.getCroptype().equals("KharifCrops")) {
			KharifCrops kc=kharifrepo.findById(message.getCropid()).get();
			List<Purchases> lsp=kc.getRequests();
			
			for(int i=0;i<lsp.size();i++) {
				if(lsp.get(i).getPurchaseid().equals(message.getPurchaseid())) {
					Purchases p =lsp.get(i);
					p.setReqstatus(message.getCropreqstatus());
					lsp.set(i, p);
					kc.setRequests(lsp);
					kharifrepo.save(kc);
				}
			}
		}else if(message.getCroptype().equals("RabbiCrops")) {
			RabbiCrops rc=rabbirepo.findById(message.getCropid()).get();
			List<Purchases> lsp=rc.getRequests();
			for(int i=0;i<lsp.size();i++) {
				Purchases p =lsp.get(i);
				if(p.getPurchaseid().equals(message.getPurchaseid())) {
					p.setReqstatus(message.getCropreqstatus());
					lsp.set(i, p);
					rc.setRequests(lsp);
					rabbirepo.save(rc);
				}
			}
		}
	}

	
}
