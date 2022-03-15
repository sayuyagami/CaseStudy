package com.agronomics.adminserver.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.agronomics.adminserver.exception.NodataException;
import com.agronomics.adminserver.models.AuthResponse;
import com.agronomics.adminserver.models.CropImages;
import com.agronomics.adminserver.models.Crops;
import com.agronomics.adminserver.models.Cropsdata;
import com.agronomics.adminserver.models.Dealers;
import com.agronomics.adminserver.models.Farmers;
import com.agronomics.adminserver.models.ListCrops;
import com.agronomics.adminserver.models.Purchases;
import com.agronomics.adminserver.repositories.CropImagesRepository;
import com.agronomics.adminserver.repositories.CropsdataRepository;



@Service
public class AdminServices {

	@Autowired
	CropsdataRepository croprepo;
	
	@Autowired
	CropImagesRepository cropimgrepo;
	
	 @Autowired
	 RestTemplate restTemplate;
	
	//get crops details
	public ResponseEntity<?> Allcrops() throws Exception {
		//ListCrops lc =new ListCrops();
		//lc.setCropdata(croprepo.findAll());
		return ResponseEntity.ok(croprepo.findAll());
	}
	
	//get crops details
	public List<Cropsdata> Allcropsbyfarmerid(long farmerid) throws Exception {

		//ListCrops lscrps = new ListCrops();
		if(croprepo.findAll()==null) { throw new Exception("No data");}
		List<Cropsdata> lskc=croprepo.findAll().stream().filter((farmer)->farmer.getFarmerid().equals(farmerid)).toList();
		if(lskc.size()==0) {
			Cropsdata c = new Cropsdata();
			return null;
		}
		//lscrps.setCropdata(lskc);
		
		return lskc;
	}
	
	//get Kharifcrops details
	public List<Cropsdata> AllKharifcrops() throws Exception {
		List<Cropsdata> lsk=croprepo.findAll().stream().filter((k)->k.getCroptype().equals("KharifCrops")).toList();
		if(lsk.size()==0) { throw new NodataException("No data");}
		else
			return lsk;
	}

	public List<Cropsdata> AllRabbicrops() throws Exception {
		// TODO Auto-generated method stub
		List<Cropsdata> lsr=croprepo.findAll().stream().filter((k)->k.getCroptype().equals("RabbiCrops")).toList();
		try {
			if(lsr.size()==0) {
				throw new NodataException("No data");
			}else {
				return lsr;
				}
			}
		catch(NodataException e) {
			e.getMessage();
		}
		return null;	
	}
	
	public List<Cropsdata> AllCashcrops() throws Exception {
		// TODO Auto-generated method stub
		List<Cropsdata> lsc=croprepo.findAll().stream().filter((k)->k.getCroptype().equals("CashCrops")).toList();
		if(lsc.size()==0) { throw new Exception("No data");}
		else
			return lsc;
	}

	public ResponseEntity<String> Receivespostsfromfarmer(Crops message)  {
	
		Cropsdata kc= new Cropsdata();
		Long num= 0L;
		if(croprepo.findAll().size()!=0) {
			num=(long) croprepo.findAll().size();
		}
			kc.setCropid(num+1);
			kc.setFarmerid(message.getFarmerid());
			kc.setCroplocation(message.getCroplocation());
			kc.setCropname(message.getCropname());
			kc.setCropprice(message.getCropprice());
			kc.setCropqty(message.getCropqty());
			kc.setCroptype(message.getCroptype());
			kc.setFarmername(message.getFarmername());
			byte[] imgval=cropimgrepo.findById(num+1).get().getPicByte();
			kc.setPicByte(decompressBytes(imgval));
			//kc.setImgid(num+1);
			//kc.setImgtitle(message.getFarmername()+num+1);
			//kc.setImage(message.getImage());
			kc.setCropstatus("Available");
			croprepo.save(kc);
			return ResponseEntity.ok("added crop");
	}
	
	public ResponseEntity<?> addimagefromfarmer(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		Cropsdata kc= new Cropsdata();
		Long num= 0L;
		if(croprepo.findAll().size()!=0) {
			num=(long) croprepo.findAll().size();
		}
		CropImages img = new CropImages(num+1,file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		cropimgrepo.save(img);
		return ResponseEntity.ok("added image");
	}

	

	// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

			return outputStream.toByteArray();
		}

		// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}

	public ResponseEntity<?> cropsbyname(String cropnm) {
		// TODO Auto-generated method stub
		Cropsdata lc=croprepo.findBycropname(cropnm);
		return ResponseEntity.ok(lc);
	}

	public void updatepostforfarmer(Crops message) {
		// TODO Auto-generated method stub
			Cropsdata kc= croprepo.findById(message.getCropid()).get();
			if(message.getFarmerid().equals(kc.getFarmerid())) {
			
				kc.setCropid(kc.getCropid());
				kc.setFarmerid(kc.getFarmerid());
				kc.setCroplocation(message.getCroplocation());
				kc.setCropname(kc.getCropname());
				kc.setCropprice(message.getCropprice());
				kc.setCropqty(message.getCropqty());
				kc.setCroptype(kc.getCroptype());
				kc.setFarmername(kc.getFarmername());
				croprepo.save(kc);
			}
		}

	public void requestcropfromdealer(Crops message) {
		// TODO Auto-generated method stub
			Cropsdata kc= croprepo.findById(message.getReqcropid()).get();
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
				croprepo.save(kc);	
	}

	public void updaterequestcropfromdealer(Crops message) {
		
			Cropsdata kc=croprepo.findById(message.getCropid()).get();
			List<Purchases> lsp=kc.getRequests();
			
			for(int i=0;i<lsp.size();i++) {
				if(lsp.get(i).getPurchaseid().equals(message.getPurchaseid())) {
					Purchases p =lsp.get(i);
					p.setReqstatus(message.getCropreqstatus());
					lsp.set(i, p);
					kc.setRequests(lsp);
					croprepo.save(kc);
				}
			}
	}


	public List<String> Allcropstypes() {
		// TODO Auto-generated method stub
		List<String> ls = new ArrayList<>();
		ls.add("KharifCrops");
		ls.add("RabbiCrops");
		ls.add("CashCrops");
		return ls;
	}

	@SuppressWarnings("unchecked")
	public List<Farmers> Allauthfarmers() {
		// TODO Auto-generated method stub
		return restTemplate.getForObject("http://API-Gateway/agrofarmer/farmers/admin/farmerslist", List.class);
	}

	public ResponseEntity<?> updtcropsbyfarmerid(long farmerid, Crops crps) {
		// TODO Auto-generated method stub
		
		List<Cropsdata> lskc=croprepo.findAll().stream().filter((farmer)->farmer.getFarmerid().equals(farmerid)).toList();
		for(int i=0;i<lskc.size();i++) {
			if(lskc.get(i).getCropid()==crps.getCropid()) {
				Cropsdata c=lskc.get(i);
				c.setCropprice(crps.getCropprice());
				croprepo.save(c);

			}	
		}
		return ResponseEntity.ok(new AuthResponse("updated crop"));
	}

	
	public ResponseEntity<?> subscribedposts(Long dealerid) {
		// TODO Auto-generated method stub
		List<Cropsdata> ls =new ArrayList<>();
		Dealers d=restTemplate.getForObject("http://API-Gateway/agrodealer/dealers/admin/"+dealerid, Dealers.class);
		
		List<Long> dealersubs=d.getSubs();
		
		for(Long i:dealersubs) {
			List<Cropsdata> lskc=croprepo.findAll().stream().filter((farmer)->farmer.getFarmerid().equals(i)).toList();
			
			for(Cropsdata c: lskc) {
				ls.add(c);
			}
			
		}
		return ResponseEntity.ok(ls);
		//return null;
	}

	public CropImages getcropimages(Long imageid) {
		// TODO Auto-generated method stub
		final Optional<CropImages> retrievedImage = cropimgrepo.findById(imageid);
		CropImages img = new CropImages(retrievedImage.get().getId(), retrievedImage.get().getName(),retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}

	public List<CropImages> getcropimageslst() {
		// TODO Auto-generated method stub
		return cropimgrepo.findAll();
	}

	
	
}
