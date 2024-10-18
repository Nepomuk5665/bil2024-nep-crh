import os
import torch
import numpy as np
from PIL import Image
from torch.utils.data import Dataset
from pycocotools.coco import COCO


class COCOStuffDataset(Dataset):
    def __init__(self, root, annFile):
        self.root = root
        self.coco = COCO(annFile)
        self.ids = list(self.coco.imgs.keys())
        self.categories = self.coco.getCatIds()
        self.num_classes = len(self.categories) + 1  # Add 1 for background class

    def __getitem__(self, index):
        img_id = self.ids[index]
        ann_ids = self.coco.getAnnIds(imgIds=img_id)
        anns = self.coco.loadAnns(ann_ids)

        path = self.coco.loadImgs(img_id)[0]['file_name']
        img = Image.open(os.path.join(self.root, path)).convert('RGB')

        # Convert PIL Image to tensor
        img = torch.from_numpy(np.array(img)).permute(2, 0, 1).float()

        # Prepare the target in the correct format
        num_objs = len(anns)
        boxes = []
        labels = []
        areas = []
        iscrowd = []
        for i in range(num_objs):
            if 'bbox' in anns[i]:
                xmin = anns[i]['bbox'][0]
                ymin = anns[i]['bbox'][1]
                xmax = xmin + anns[i]['bbox'][2]
                ymax = ymin + anns[i]['bbox'][3]
                boxes.append([xmin, ymin, xmax, ymax])
                # Map category_id to a zero-based index
                label = self.categories.index(anns[i]['category_id']) + 1  # Add 1 to reserve 0 for background
                labels.append(label)
                areas.append(anns[i]['area'])
                iscrowd.append(anns[i]['iscrowd'])

        target = {}
        if boxes:
            target["boxes"] = torch.as_tensor(boxes, dtype=torch.float32)
            target["labels"] = torch.as_tensor(labels, dtype=torch.int64)
            target["area"] = torch.as_tensor(areas, dtype=torch.float32)
            target["iscrowd"] = torch.as_tensor(iscrowd, dtype=torch.int64)
        else:
            # If no boxes, create dummy tensors
            target["boxes"] = torch.zeros((0, 4), dtype=torch.float32)
            target["labels"] = torch.zeros((0,), dtype=torch.int64)
            target["area"] = torch.zeros((0,), dtype=torch.float32)
            target["iscrowd"] = torch.zeros((0,), dtype=torch.int64)

        target["image_id"] = torch.tensor([img_id])

        return img, target

    def __len__(self):
        return len(self.ids)


def load_dataset(data_dir, ann_file):
    return COCOStuffDataset(data_dir, ann_file)